package es.reaktor.reaktorclient.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.reaktor.reaktorclient.utils.exceptions.ReaktorClientException;

public class MessageSender extends Thread
{

    /**
     * - Logger -
     * This logger is used to log the information of the application
     */
    private static final Logger LOGGER = LogManager.getLogger();

    private final CommandExecutor commandExecutor;

    private String message;

    public MessageSender(CommandExecutor commandExecutor, String message)
    {
        this.commandExecutor = commandExecutor;
        this.message = "msg * /self /w \""+ message +  "\"\n";
    }

    @Override
    public void run()
    {
        try
        {
            this.commandExecutor.executeCommand(this.message);
        }
        catch (ReaktorClientException reaktorClientException)
        {
            LOGGER.warn("Error sending message", reaktorClientException);
        }
    }

    public void setMalware(String malwareListInThisPc)
    {
        this.message += "Malware detected: " + malwareListInThisPc;
    }
}
