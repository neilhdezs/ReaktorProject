package es.reaktor.reaktorclient.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.reaktor.reaktorclient.utils.exceptions.ReaktorClientException;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageSender extends Thread
{

    public static final String COMMAND = "Add-Type -TypeDefinition @\"\n" +
            "using System;\n" +
            "using System.Runtime.InteropServices;\n" +
            "public class MessagePopup\n" +
            "{\n" +
            "    [DllImport(\"user32.dll\", CharSet = CharSet.Auto)]\n" +
            "    public static extern int MessageBox(int hWnd, string text, string caption, int options);\n" +
            "}\n" +
            "\"@\n" +
            "[MessagePopup]::MessageBox(0,\"Estas siendo monitorizado por el sistema del I.E.S Jandula, cierra los siguientes programas, se tomaran medidas si no lo haces.`nMALWARE detectado: %s\", \"AVISO DE MALWARE AL RESPONSABLE\", 0)";

    /**
     * - Logger -
     * This logger is used to log the information of the application
     */
    private static final Logger LOGGER = LogManager.getLogger();

    private final WriteFiles writeFiles;

    private final CommandExecutor commandExecutor;

    private final String command;

    private String message;

    public MessageSender(CommandExecutor commandExecutor)
    {

        this.writeFiles = new WriteFiles();

        this.commandExecutor = commandExecutor;

        this.command = "powershell.exe -ExecutionPolicy Bypass -File " + Constants.PATH_MALWARE_FILE + "\n";
    }

    @Override
    public void run()
    {
        try
        {
            this.writeFiles.crearScriptMalware(this.message);
            this.commandExecutor.executeCommand(this.command);
            this.writeFiles.deleteScriptMalware();
        }
        catch (ReaktorClientException reaktorClientException)
        {
            LOGGER.warn("Error sending message", reaktorClientException);
        }
    }

    public void setMalware(String malwareListInThisPc)
    {
        this.message = String.format(COMMAND, malwareListInThisPc);
    }
}
