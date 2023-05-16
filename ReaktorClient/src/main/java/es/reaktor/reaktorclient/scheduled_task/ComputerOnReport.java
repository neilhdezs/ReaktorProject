package es.reaktor.reaktorclient.scheduled_task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.reaktor.reaktorclient.utils.HttpCommunicationSender;
import es.reaktor.reaktorclient.utils.exceptions.ConstantsErrors;
import es.reaktor.reaktorclient.utils.exceptions.ReaktorClientException;
import es.reaktor.reaktorclient.windows.WindowsMotherboard;

@Component
public class ComputerOnReport
{

    /**
     * - Logger -
     * This logger is used to log the information of the application
     */
    private static final Logger LOGGER = LogManager.getLogger();


    @Autowired
    private HttpCommunicationSender httpCommunicationSender;

    @Autowired
    private WindowsMotherboard windowsMotherboard;

    @Value("${reaktor.server.url}")
    private String reaktorServerUrl;

    @Scheduled(fixedRate = 10_000)
    public void computerOnReport()
    {
        try
        {
            this.httpCommunicationSender.sendPost(this.httpCommunicationSender.createHttpPostWithHeader(this.reaktorServerUrl + "/computer-on", "serialNumber", this.windowsMotherboard.getHardwareUUID()));
            LOGGER.info("Computer on report sent");
        }
        catch (ReaktorClientException reaktorClientException)
        {
            LOGGER.warn(reaktorClientException.getMessage());
            LOGGER.warn(ConstantsErrors.ERROR_COMMUNICATION_TO_SERVER, reaktorClientException);
            reaktorClientException.printStackTrace();
        }
    }
}
