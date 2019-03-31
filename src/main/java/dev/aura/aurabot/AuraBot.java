package dev.aura.aurabot;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import org.apache.commons.cli.CommandLine;

@Log4j2
public class AuraBot {
  @Getter private static JDA JDA;

  public static void main(CommandLine commandLine) throws Exception {
    JDA =
        new JDABuilder(AccountType.BOT)
            .setToken(commandLine.getOptionValue(BotMain.OPT_TOKEN))
            .build()
            .awaitReady();

    // Start the CommandClient
    JDA.addEventListener(CommandHandler.getClient());

    // Add Listeners Below
    // JDA.addEventListener();

    // Set Presence
    JDA.getPresence().setGame(Game.watching("Code fly past"));
  }
}
