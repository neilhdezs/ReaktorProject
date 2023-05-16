package es.reaktor.reaktorclient.windows;

import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import es.reaktor.models.Configuration;
import es.reaktor.models.Motherboard;
import es.reaktor.reaktorclient.utils.CommandExecutor;
import es.reaktor.reaktorclient.utils.Constants;
import es.reaktor.reaktorclient.utils.HttpCommunicationSender;
import es.reaktor.reaktorclient.utils.ReadFiles;
import es.reaktor.reaktorclient.utils.exceptions.ConstantsErrors;
import es.reaktor.reaktorclient.utils.exceptions.ReaktorClientException;
import oshi.SystemInfo;
import oshi.hardware.platform.windows.WindowsHardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

import java.util.Date;
import java.util.HashSet;

/**
 * - Class -
 * This class is used to get the hardware information of the computer in Windows OS
 */
@Service
public final class WindowsMotherboard
{

    /**
     * - Logger -
     * This logger is used to log the information of the application
     */
    private final Logger LOGGER = LogManager.getLogger();


    /**
     * - Attribute -
     * this class is used to read the files of the application
     */
    @Autowired
    private ReadFiles readFiles;

    /**
     * - Attribute -
     * this class is used to execute the commands of the application
     */
    @Autowired
    private CommandExecutor commandExecutor;

    /**
     * - Attribute -
     * this class is used to send the information to the server
     */
    @Autowired
    private HttpCommunicationSender httpCommunicationSender;

    /**
     * - Attribute -
     * this Attribute is used to get the URL of the server
     */
    @Value("${reaktor.server.url}")
    private String reaktorServerUrl;

    /**
     * - Attribute -
     * this class is used to get the hardware information of the computer
     */
    private final WindowsHardwareAbstractionLayer hardwareAbstractionLayer;

    /**
     * - Attribute -
     * this class is used to get information of the Operating System
     */
    private final OperatingSystem operatingSystem;

    /**
     * - Attribute -
     * this class is used to get information of the configuration introduced by the user
     */
    private Configuration configuration;


    /**
     * - Constructor -
     * Constructor by default, is used to initialize the hardwareAbstractionLayer attribute
     */
    public WindowsMotherboard()
    {
        this.hardwareAbstractionLayer   = new WindowsHardwareAbstractionLayer();
        this.operatingSystem            = new SystemInfo().getOperatingSystem();
    }

    /**
     * - Method -
     * This method is used to initialize the configuration attribute
     */
    @PostConstruct
    public void init()
    {
        this.configuration = this.readFiles.readConfiguration();
    }



    /**
     * - Method -
     * This method is used to get the information of the motherboard
     * @return Motherboard with the information
     */
    public Motherboard getMotherboard()
    {
        Motherboard motherboard = new Motherboard();

        motherboard.setSerialNumber(this.getHardwareUUID());
        motherboard.setModel(this.getModel());
        motherboard.setLastConnection(this.getLastConnection());
        motherboard.setLastUpdateComputerOn(this.getLastUpdateComputerOn(motherboard));
        motherboard.setComputerOn(true);
        motherboard.setMalware(new HashSet<>());
        this.setLocationAndProfessorName(motherboard);

        return motherboard;
    }

    /**
     * - Method -
     * This method is used to set the location and the professor name
     */
    private void setLocationAndProfessorName(Motherboard motherboard)
    {
        motherboard.setProfessor(this.configuration.getProfessor());
        motherboard.setClassroom(this.configuration.getClassroom());
        motherboard.setDescription(this.configuration.getDescription());
    }

    /**
     * - Method -
     * This method is used to get the last update of the computer
     * @param motherboard Motherboard with the information of the computer
     * @return Date with the last update of the computer
     */
    private Date getLastUpdateComputerOn(Motherboard motherboard)
    {
        try
        {
            this.httpCommunicationSender.sendPost(this.httpCommunicationSender.createHttpPostWithHeader(this.reaktorServerUrl + "/computer-on", "serialNumber", motherboard.getSerialNumber()));
        }
        catch (ReaktorClientException reaktorClientException)
        {
            LOGGER.warn(reaktorClientException.getMessage());
            LOGGER.warn(ConstantsErrors.ERROR_COMMUNICATION_TO_SERVER, reaktorClientException);
            reaktorClientException.printStackTrace();
        }
        return new Date();
    }

    /**
     * - Method -
     * This method is used to get the UUID of the computer
     * @return String with the UUID of the computer
     */
    public String getHardwareUUID()
    {
        return this.hardwareAbstractionLayer.getComputerSystem().getHardwareUUID();
    }

    /**
     * - Method -
     * This method is used to get the Last connection
     * @return Date with the Last connection
     */
    public Date getLastConnection()
    {
        long lastBootTime = operatingSystem.getSystemUptime();
        long lastConnectionTime = System.currentTimeMillis() - lastBootTime;
        return new Date(lastConnectionTime);

    }

    /**
     * - Method -
     * This method is used to get the motherboard model
     * @return String with the motherboard model of the computer
     */
    public String getModel()
    {
        String model;
        try
        {
            model = this.commandExecutor.executeCommand(CommandConstantsWindows.GET_MODEL_MOTHERBOARD_WINDOWS).replace(Constants.OUTPUT_MODEL_CONSOLE, Constants.EMPTY_STRING).trim();
        }
        catch (ReaktorClientException reaktorClientException)
        {
            model = Constants.UNKNOWN;
            LOGGER.warn(ConstantsErrors.ERROR_GETTING_HARDWARE_MODEL_MOTHERBOARD, reaktorClientException);
            reaktorClientException.printStackTrace();
        }

        return model;
    }

}
