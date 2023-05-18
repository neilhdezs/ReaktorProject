package es.reaktor.reaktorclient.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.reaktor.reaktorclient.utils.exceptions.ReaktorClientException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.util.Locale;

@Service
public class BeepSound extends Thread
{

    /**
     * - Logger -
     * This logger is used to log the information of the application
     */
    private static final Logger LOGGER = LogManager.getLogger();

    private final CommandExecutor commandExecutor;

    public BeepSound(CommandExecutor commandExecutor)
    {
        this.commandExecutor = commandExecutor;
    }

    @Override
    public void run()
    {
        try
        {
            String command = Paths.get("ReaktorClient/src/main/resources/Debug/net6.0/BeepReaktor.exe").toFile().getAbsolutePath();
            this.commandExecutor.executeCommand(command);
        }
        catch (ReaktorClientException reaktorClientException)
        {
            LOGGER.warn("Error this comand doesn`t work", reaktorClientException);
        }
    }
}
