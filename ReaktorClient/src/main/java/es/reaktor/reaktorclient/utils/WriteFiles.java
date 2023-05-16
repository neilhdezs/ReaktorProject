package es.reaktor.reaktorclient.utils;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectWriter;
import es.reaktor.reaktorclient.utils.exceptions.ConstantsErrors;
import es.reaktor.reaktorclient.utils.exceptions.ReaktorClientException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class WriteFiles
{

    public void escribirResultadoJson(Object objectToJson) throws ReaktorClientException
    {
        try
        {
            ObjectWriter writer = Json.mapper().writer(new DefaultPrettyPrinter());
            writer.writeValue(new File(Constants.PATH_CONFIG_FILE), objectToJson);
        }
        catch (IOException streamWriteException)
        {
            throw new ReaktorClientException(ConstantsErrors.ERROR_WRITE_FILE, "500", streamWriteException);
        }
    }

}
