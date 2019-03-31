package dev.aura.aurabot;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;

@Log4j2
public class AuraBot {
  public static final String NAME = "@name@";
  public static final String VERSION = "@version@";
  public static final String DESCRIPTION = "@description@";
  public static final String LICENSE = "MIT License\nCopyright (c) 2019 Aura Development Team";

  private static final String VERSION_TEXT = NAME + " v" + VERSION + '\n' + LICENSE;
  private static final String HELP_HEADER = '\n' + NAME + ": " + DESCRIPTION + "\n\nParameters:";
  private static final String HELP_FOOTER = '\n' + VERSION_TEXT;

  protected static final String OPT_TOKEN = "t";
  protected static final String OPT_DEBUG = "d";
  protected static final String OPT_HELP = "h";
  protected static final String OPT_VERBOSE = "v";
  protected static final String OPT_VERSION = "V";

  @Getter @Setter private static volatile int returnStatus = -1;

  @Getter private static JDA JDA;

  private static Options getOptions() {
    final OptionGroup mode = new OptionGroup();

    mode.setRequired(true);
    mode.addOption(new Option(OPT_TOKEN, "token", true, "The bot token."));
    mode.addOption(new Option(OPT_VERSION, "version", false, "Print the version."));
    mode.addOption(new Option(OPT_HELP, "help", false, "Print this message."));

    final Options options = new Options();

    options.addOptionGroup(mode);
    options.addOption(OPT_DEBUG, "debug", false, "Enable more verbose logging.");
    options.addOption(
        OPT_VERBOSE,
        "verbose",
        false,
        "Displays all messages that are printed to file.\nWill be very spammy in combination with -d!");

    return options;
  }

  private static File getJar() {
    return new File(AuraBot.class.getProtectionDomain().getCodeSource().getLocation().getPath());
  }

  private static void updateLogLevel(Level oldLevel, Level newLevel) {
    final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
    final Configuration config = ctx.getConfiguration();

    config
        .getLoggers()
        .values()
        .forEach(
            logger -> {
              if (logger.getLevel() == oldLevel) {
                logger.setLevel(newLevel);
              }

              logger
                  .getAppenderRefs()
                  .stream()
                  .filter(appenderRef -> appenderRef.getLevel() == oldLevel)
                  .forEach(
                      appenderRef -> {
                        final String name = appenderRef.getRef();

                        logger.removeAppender(name);
                        logger.addAppender(
                            config.getAppender(name), newLevel, appenderRef.getFilter());
                      });
            });

    ctx.updateLoggers();
  }

  private static void enableTrace() {
    updateLogLevel(Level.DEBUG, Level.TRACE);
  }

  private static void enableVerbose(final boolean debug) {
    updateLogLevel(Level.INFO, debug ? Level.TRACE : Level.DEBUG);
  }

  protected static CommandLine parseParameters(String[] args) {
    final HelpFormatter formatter = new HelpFormatter();
    final Options options = getOptions();
    final PrintWriter stdErr =
        new PrintWriter(new OutputStreamWriter(System.err, StandardCharsets.UTF_8), true);
    final String invocation = "java -jar " + getJar().getName();
    final int terminalWidth = 100;

    try {
      logger.debug("Parsing parameters: {}", Arrays.toString(args));

      final CommandLineParser parser = new DefaultParser();
      final CommandLine commandLine = parser.parse(options, args);

      logger.debug("Options:");
      Arrays.stream(commandLine.getOptions())
          .forEach(
              option ->
                  logger.debug(
                      "  {}: {}",
                      option.hasLongOpt() ? option.getLongOpt() : option.getOpt(),
                      option.getValuesList()));
      logger.debug("Arguments: ");
      logger.debug("  {}", commandLine.getArgList());

      if (commandLine.hasOption(OPT_HELP)) {
        logger.debug("Help flag detected. Printing help message and shutting down.");

        formatter.printHelp(
            stdErr,
            terminalWidth,
            invocation,
            HELP_HEADER,
            options,
            HelpFormatter.DEFAULT_LEFT_PAD,
            HelpFormatter.DEFAULT_DESC_PAD,
            HELP_FOOTER,
            true);

        setReturnStatus(0);
        return null;
      } else if (commandLine.hasOption(OPT_VERSION)) {
        logger.debug("Version flag detected. Printing version and shutting down.");

        System.err.println(VERSION_TEXT);

        setReturnStatus(0);
        return null;
      }

      final boolean verbose = commandLine.hasOption(OPT_VERBOSE);
      final boolean debug = commandLine.hasOption(OPT_DEBUG);

      if (verbose) {
        logger.debug("Verbose active. Enabling Verbose logging!");
        enableVerbose(debug);
        logger.debug("Verbose logging Enabled.");
      }

      if (debug) {
        logger.debug("Debug active. Enabling TRACE logging!");
        enableTrace();
        logger.trace("TRACE logging Enabled.");
      }

      return commandLine;
    } catch (ParseException exp) {
      logger.debug("Error while parsing command line:", exp);

      System.err.println("Error: " + exp.getMessage());
      formatter.printUsage(stdErr, terminalWidth, invocation, options);

      setReturnStatus(1);
      return null;
    }
  }

  public static void main(String[] args) {
    try {
      logger.debug("Registering UncaughtExceptionLogger");
      Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionLogger());

      final CommandLine commandLine = parseParameters(args);

      if (commandLine != null) {
        try {
          logger.info("Starting " + NAME + " v" + VERSION);

          main(commandLine);

        } catch (RuntimeException e) {
          logger.error("Error during startup: {}", e.getMessage());
          logger.debug("Detailed error:", e);
          logger.info("Check your settings!");

          setReturnStatus(1);
        }
      }
    } catch (Exception e) {
      logger.fatal("Fatal Exception in Application:", e);

      setReturnStatus(2);
    }

    shutdown();
    LogManager.shutdown();

    System.exit(returnStatus);
  }

  public static void main(CommandLine commandLine) throws Exception {
    JDA =
        new JDABuilder(AccountType.BOT)
            .setToken(commandLine.getOptionValue(OPT_TOKEN))
            .build()
            .awaitReady();

    // Start the CommandClient
    JDA.addEventListener(CommandHandler.getClient());

    // Add Listeners Below
    // JDA.addEventListener();

    // Set Presence
    JDA.getPresence().setGame(Game.watching("Code fly past"));
  }

  protected static void shutdown() {
    logger.debug("Starting shutdown routine");

    // Anything that needs to be shut down

    if (returnStatus == 0) {
      logger.info("Application stopped.");
    } else {
      logger.warn("Application stopped with exit value {}.", returnStatus);
    }
  }

  @Log4j2
  protected static class UncaughtExceptionLogger implements UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
      logger.fatal("Uncaught exception in thread \"" + thread.getName() + '"', exception);
    }
  }
}
