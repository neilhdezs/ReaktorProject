package es.reaktor.reaktorclient.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.reaktor.models.Configuration;
import es.reaktor.reaktorclient.utils.exceptions.ConstantsErrors;
import es.reaktor.reaktorclient.utils.exceptions.ReaktorClientException;

import java.util.*;

/**
 * - CLASS -
 * This class is used to execute the action of the arguments
 */
@Service
public class ActionsArguments
{

    @Autowired
    private WriteFiles writeFiles;

    /**
     * This method is used to execute the action of the arguments
     * @param args Arguments of the command line
     */
    public void actionArguments(String[] args) throws ReaktorClientException
    {
        // If the arguments are null, we return and execute normal
        if (args.length == 0)
        {
            return;
        }

        // If the first argument is help, we execute the help command
        if (args[0].equalsIgnoreCase(Constants.HELP_PARAMETERS))
        {
            actionHelp();
            return;
        }

        // If the first argument is "-a" or "-p" we execute the action
        writeConfiguration(args);

    }



    private void writeConfiguration(String[] args) throws ReaktorClientException
    {

        Map<String, String> argumentsContent =  obtainsArguments(args);

        Configuration configuration = createConfiguration(argumentsContent);

        checkConfiguration(configuration);

        this.writeFiles.escribirResultadoJson(configuration);

    }

    public void checkConfiguration(Configuration configuration)
    {
        if (configuration.getClassroom() == null || configuration.getClassroom().isEmpty())
        {
            configuration.setClassroom(Constants.UNKNOWN);
        }

        if (configuration.getProfessor() == null || configuration.getProfessor().isEmpty())
        {
            configuration.setProfessor(Constants.UNKNOWN);
        }

        if (configuration.getDescription() == null || configuration.getDescription().isEmpty())
        {
            configuration.setDescription(Constants.UNKNOWN);
        }
    }

    /**
     * This method is used to obtain the arguments and the content of the arguments
     * @param args Arguments of the command line
     * @return Map with the arguments and the content of the arguments
     */
    private Map<String, String> obtainsArguments(String[] args)
    {

        // Convert the array of arguments to a list
        List<String> argsList = List.of(args);

        // String: Letter, Integer: Index of the option
        Map<String, Integer> argsIndex = new HashMap<>();

        // String: Letter, String: Content of the option
        Map<String, String> argsAllContent;

        // Extract the arguments and the indices of the arguments
        extractedArguments(argsList, argsIndex);

        // Create a list of the entry map
        List<Map.Entry<String, Integer>> entryArrayList = new ArrayList<>(argsIndex.entrySet());

        // Order the list by the value of the map
        entryArrayList.sort(Map.Entry.comparingByValue());

        // Check if the arguments content is correct
        checkerContentArguments(entryArrayList, argsList);

        // Extract the content of the arguments
        argsAllContent = extractedInformationOfArguments(argsList, entryArrayList);

        return argsAllContent;
    }

    /**
     * This method is used to check the content of the arguments
     * @param argsList List of arguments
     * @param entryOptionsArguments List of options arguments and indices
     */
    private Map<String, String> extractedInformationOfArguments(List<String> argsList, List<Map.Entry<String, Integer>> entryOptionsArguments)
    {

        Map<String, String> argsAllContent = new HashMap<>();

        List<String> content;
        // Si solo tiene un argumento
        if (entryOptionsArguments.size() <= 1) // Es decir que tiene un argumento
        {
            // Si el argumento es el primero, cogemos el contenido desde el siguiente indice hasta el final
            content = argsList.subList(entryOptionsArguments.get(0).getValue() + 1, argsList.size());

            // Almacenamos en el mapa el contenido del argumento
            argsAllContent.put(entryOptionsArguments.get(0).getKey(), String.join(" ", content));
        }
        // Si tiene mas de un argumento
        else
        {
            // Recorremos la lista de argumentos
            for (int i = 0; i < entryOptionsArguments.size() - 1; i++)
            {
                // Nos quedamos con el contenido del argumento
                content = argsList.subList(entryOptionsArguments.get(i).getValue() + 1, entryOptionsArguments.get(i + 1).getValue());

                // Almacenamos en el mapa el contenido del argumento con su propio argumento
                argsAllContent.put(entryOptionsArguments.get(i).getKey(), String.join(" ", content));
            }

            // Nos quedamos con el contenido del ultimo argumento ya que no tiene argumento siguiente
            content = argsList.subList(entryOptionsArguments.get(entryOptionsArguments.size() - 1).getValue() + 1, argsList.size());

            argsAllContent.put(entryOptionsArguments.get(entryOptionsArguments.size() - 1).getKey(), String.join(" ", content));
        }

        return argsAllContent;
    }


    private Configuration createConfiguration(Map<String, String> argsAllContent)
    {
        Configuration configuration = new Configuration();

        if (argsAllContent.containsKey(Constants.CLASSROOM_PARAMETERS))
        {
            configuration.setClassroom(argsAllContent.get(Constants.CLASSROOM_PARAMETERS));
        }

        if (argsAllContent.containsKey(Constants.PROFESSOR_PARAMETERS))
        {
            configuration.setProfessor(argsAllContent.get(Constants.PROFESSOR_PARAMETERS));
        }

        if (argsAllContent.containsKey(Constants.DESCRIPTION_PARAMETERS))
        {
            configuration.setDescription(argsAllContent.get(Constants.DESCRIPTION_PARAMETERS));
        }
        return configuration;
    }

    /**
     * This method is used to extract the arguments and the content of the arguments
     * @param argsContent Map with the options arguments and the index of the arguments
     * @param argsList List with the all arguments
     */
    private void checkerContentArguments(List<Map.Entry<String, Integer>> argsContent, List<String> argsList)
    {
        // if only one parameter
        if (argsContent.size() <= 1)
        {
            // if the only one argument
            if (argsList.size()<= 1)
            {
                // no arguments found
                throw new IllegalArgumentException(ConstantsErrors.ERROR_ARGUMENTS_NOT_FOUND);
            }
        }
        // if more than one parameter
        else
        {
            // loop to check if the arguments content is empty
            for (int i = 0; i < argsContent.size() - 1; i++)
            {
                // if the index current argument options, is equal to the next index argument options
                if (argsContent.get(i).getValue() + 1 == argsContent.get(i + 1).getValue())
                {
                    throw new IllegalArgumentException(ConstantsErrors.ERROR_ARGUMENTS_NOT_FOUND);
                }
            }
            // As it is possible that it has no more arguments, we check if the index of the option is the last one, i.e. it has no more arguments.
            if (argsContent.get(argsContent.size() - 1).getValue().equals(argsList.size() - 1))
            {
                throw new IllegalArgumentException(ConstantsErrors.ERROR_ARGUMENTS_NOT_FOUND);
            }
        }
    }

    /**
     * This method is used to extract the arguments and the index of the arguments
     * @param arguments Arguments of the command line
     * @param argsIndice Map for storage the arguments and the index of the arguments
     */
    private static void extractedArguments(List<String> arguments, Map<String, Integer> argsIndice)
    {
        // if contains the argument "-a"
        if (arguments.contains(Constants.CLASSROOM_PARAMETERS))
        {
            // we add the argument and the index of the argument
            argsIndice.put(arguments.get(arguments.indexOf(Constants.CLASSROOM_PARAMETERS)), arguments.indexOf(Constants.CLASSROOM_PARAMETERS));
        }
        // if contains the argument "-p"
        if (arguments.contains(Constants.PROFESSOR_PARAMETERS))
        {
            // we add the argument and the index of the argument
            argsIndice.put(arguments.get(arguments.indexOf(Constants.PROFESSOR_PARAMETERS)), arguments.indexOf(Constants.PROFESSOR_PARAMETERS));
        }
        // if contains the argument "-d"
        if (arguments.contains(Constants.DESCRIPTION_PARAMETERS))
        {
            // we add the argument and the index of the argument
            argsIndice.put(arguments.get(arguments.indexOf(Constants.DESCRIPTION_PARAMETERS)), arguments.indexOf(Constants.DESCRIPTION_PARAMETERS));
        }
    }

    /**
     * This method is used to print the help of the command line
     */
    public void actionHelp()
    {
        System.out.println(Constants.HELP_COMMAND_OUTPUT);
    }

}
