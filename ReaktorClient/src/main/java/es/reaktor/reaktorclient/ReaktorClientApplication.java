package es.reaktor.reaktorclient;

import es.reaktor.reaktorclient.models.Reaktor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import es.reaktor.reaktorclient.utils.ActionsArguments;
import es.reaktor.reaktorclient.utils.CheckerArguments;
import es.reaktor.reaktorclient.utils.HttpCommunicationSender;
import es.reaktor.reaktorclient.utils.exceptions.ConstantsErrors;
import es.reaktor.reaktorclient.utils.exceptions.ReaktorClientException;


/**
 * - CLASS -
 * This class is encharged of run the applications Client
 */
@SpringBootApplication
@EnableScheduling
@EntityScan(basePackages = "es.reaktor.models")
public class ReaktorClientApplication implements CommandLineRunner
{

    /**
     * - Logger -
     * This logger is used to log the information of the application
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * - Attribute -
     * this class is used to get the information of the Computer
     */
    @Autowired
    private Reaktor reaktor;


    /**
     * - Attribute -
     * This class is used to manage communications with the server.
     */
    @Autowired
    private HttpCommunicationSender httpCommunicationSender;

    /**
     * - Attribute -
     * This attrubte Storage the information about of Server URL
     */
    @Value("${reaktor.server.url}")
    private String reaktorServerUrl;

    /**
     * - Attribute -
     * this class is used to execute the arguments of the application
     */
    @Autowired
    private ActionsArguments actionsArguments;

    public static void main(String[] args) throws InterruptedException
    {

        // This attribute is used to check the arguments of the application
        CheckerArguments checkerArguments = new CheckerArguments();

        // If the arguments are incorrect exit the application
        if (!checkerArguments.checkArguments(args))
        {
            LOGGER.info("Arguments are incorrect");
            throw new IllegalArgumentException(ConstantsErrors.ERROR_ARGUMENTS_NOT_FOUND);
        }

        // If the arguments are correct start the application
        SpringApplication.run(ReaktorClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {

        try
        {
            // We carry out the actions with the arguments that are passed on to us
            this.actionsArguments.actionArguments(args);
            
            // Send the update information to Server REAKTOR
            LOGGER.info("Sending information to server REAKTOR");
            this.httpCommunicationSender.sendPost(this.httpCommunicationSender.createHttpPostReaktor(this.reaktorServerUrl+"/reaktor", this.reaktor));

        }
        catch (ReaktorClientException reaktorClientException)
        {
            LOGGER.error("Error in the application", reaktorClientException);
        }


    }
}
