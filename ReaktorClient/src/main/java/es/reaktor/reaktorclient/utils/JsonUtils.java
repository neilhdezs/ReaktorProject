package es.reaktor.reaktorclient.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.type.TypeFactory;

import es.reaktor.reaktorclient.utils.exceptions.ConstantsErrors;
import es.reaktor.reaktorclient.utils.exceptions.ReaktorClientException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import static es.reaktor.reaktorclient.utils.Json.mapper;

import java.util.Collections;
import java.util.List;

@Service
public class JsonUtils
{

    /**
     * - Logger -
     * This logger is used to log the information of the application
     */
    private final Logger LOGGER = LogManager.getLogger();

    /**
     * Parse an object to JSON in Pretty format
     *
     * @param objectToJson object to be parsed
     * @return String with JSON in Pretty format
     */
    public String writeObjectToJsonAsStringPretty(Object objectToJson) throws ReaktorClientException
    {
        String resultJsonAsString = "";
        try
        {
            // Write Result Pretty Format
            resultJsonAsString = mapper().writerWithDefaultPrettyPrinter().writeValueAsString(objectToJson);
        } catch (JsonProcessingException jsonProcessingException)
        {
            LOGGER.error(ConstantsErrors.ERROR_PARSING_OBJECT_TO_JSON, jsonProcessingException);
            throw new ReaktorClientException(ConstantsErrors.ERROR_PARSING_OBJECT_TO_JSON, "Error a la Hora de transformar el objeto a Json", jsonProcessingException);
        }

        return resultJsonAsString;
    }

    public <T> List<T> fromJsonToList(String json, Class<T> clazz) throws Exception {
        List<T> list;
        if (json.trim().equals("[]")) {
            list = Collections.emptyList();
        } else {
            list = Json.mapper().readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
        }
        return list;
    }

}
