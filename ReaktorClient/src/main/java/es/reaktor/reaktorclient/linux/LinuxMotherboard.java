package es.reaktor.reaktorclient.linux;

import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.reaktor.models.Configuration;
import es.reaktor.reaktorclient.utils.CommandExecutor;
import es.reaktor.reaktorclient.utils.ReadFiles;
import es.reaktor.reaktorclient.utils.exceptions.ConstantsErrors;
import es.reaktor.reaktorclient.utils.exceptions.ReaktorClientException;

@Service
public final class LinuxMotherboard
{

    /**
     * - Logger -
     * This logger is used to log the information of the application
     */
    private final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ReadFiles readFiles;

    /**
     * - Attribute -
     * this attribute is used to get the name of the classroom
     */
    private String locationAula;

    /**
     * - Attribute -
     * this attribute is used to get the name of the professor
     */
    private String professorName;

    private Configuration configuration;


    @Autowired
    private CommandExecutor commandExecutor;

    public LinuxMotherboard()
    {
        this.locationAula = locationAula;
        this.professorName = professorName;
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
     * This method is used to get the UUID of the computer in Linux
     * <br>
     * - ATTRIBUTE IN DATABASE -
     * serial_number: String with the UUID of the computer in Linux
     *
     * @return String with the UUID of the computer in Linux
     */
    public String getHardwareUUID()
    {
        String hardwareUUIDForLinux;

        try
        {
            hardwareUUIDForLinux = this.commandExecutor.executeCommand(CommandConstantsLinux.GET_UUID_FOR_LINUX);
        }
        catch (ReaktorClientException reaktorClientException)
        {
            hardwareUUIDForLinux = ConstantsErrors.ERROR_GETTING_HARDWARE_UUID_LINUX;
            LOGGER.warn(ConstantsErrors.ERROR_GETTING_HARDWARE_UUID_LINUX, reaktorClientException);
            reaktorClientException.printStackTrace();
        }

        return hardwareUUIDForLinux;
    }

    /**
     * - Method -
     * This method is used to get the location of the computer
     * @return String with the location of the computer
     */
    public String getLocation()
    {
        return this.configuration.getProfessor() + " : " + this.configuration.getClassroom();
    }

}
