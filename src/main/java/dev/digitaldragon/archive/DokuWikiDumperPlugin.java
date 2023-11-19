package dev.digitaldragon.archive;

import dev.digitaldragon.jobs.dokuwiki.DokuWikiDumperJob;
import dev.digitaldragon.parser.CommandLineParser;
import dev.digitaldragon.util.AfterTask;
import dev.digitaldragon.util.CommandTask;
import dev.digitaldragon.interfaces.irc.IRCClient;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.ThreadChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.UUID;

public class DokuWikiDumperPlugin extends ListenerAdapter {
    /**
     * Returns a CommandLineParser with predefined options for parsing DokuWikiDumper parameters from text-only interfaces.
     *
     * @return The CommandLineParser containing the predefined options.
     */
    public static CommandLineParser getCommandLineParser() {
        CommandLineParser parser = new CommandLineParser();
        parser.addBooleanOption("ignore-disposition-header-missing");
        parser.addIntOption("retry");
        parser.addIntOption("hard-retry");
        parser.addDoubleOption("delay");
        parser.addIntOption("threads");
        //parser.addBooleanOption("ignore-errors"); todo
        parser.addBooleanOption("ignore-action-disabled-edit");
        parser.addBooleanOption("insecure");
        parser.addBooleanOption("content");
        parser.addBooleanOption("media");
        parser.addBooleanOption("html");
        parser.addBooleanOption("pdf");
        parser.addBooleanOption("auto");
        parser.addBooleanOption("current-only");
        parser.addStringOption("explain");
        parser.addUrlOption("url");
        return parser;
    }

    /**
     * Converts a CommandLineParser object generated by {@link DokuWikiDumperPlugin#getCommandLineParser()} into a string representation of the options.
     *
     * @param commandLineParser The CommandLineParser object to convert.
     * @return A string representation of the options.
     */
    public static String parserToOptions(CommandLineParser commandLineParser) {
        StringBuilder options = new StringBuilder();
        parseInt("retry", commandLineParser, options);
        parseInt("hard-retry", commandLineParser, options);
        parseInt("threads", commandLineParser, options);

        parseDouble("delay", commandLineParser, options);


        parseBoolean("ignore-disposition-header-missing", commandLineParser, options);
        //parseBoolean("ignore-errors", commandLineParser, options); todo
        parseBoolean("ignore-action-disabled-edit", commandLineParser, options);
        parseBoolean("insecure", commandLineParser, options);
        parseBoolean("content", commandLineParser, options);
        parseBoolean("media", commandLineParser, options);
        parseBoolean("html", commandLineParser, options);
        parseBoolean("pdf", commandLineParser, options);
        parseBoolean("auto", commandLineParser, options);
        parseBoolean("current-only", commandLineParser, options);

        options.append("--upload ");

        if (commandLineParser.getOption("url") != null) {
            options.append(commandLineParser.getOption("url"));
            options.append(" ");
        }

        return options.toString();
    }

    private static void parseBoolean(String name, CommandLineParser commandLineParser, StringBuilder options) {
        if (commandLineParser.getOption(name) == Boolean.TRUE) {
            options.append("--").append(name).append(" ");
        }
    }

    private static void parseInt(String name, CommandLineParser commandLineParser, StringBuilder options) {
        if (commandLineParser.getOption(name) instanceof Integer) {
            options.append("--").append(name).append(" ").append(commandLineParser.getOption(name)).append(" ");
        }
    }

    private static void parseDouble(String name, CommandLineParser commandLineParser, StringBuilder options) {
        if (commandLineParser.getOption(name) instanceof Double) {
            options.append("--").append(name).append(" ").append(commandLineParser.getOption(name)).append(" ");
        }
    }

}
