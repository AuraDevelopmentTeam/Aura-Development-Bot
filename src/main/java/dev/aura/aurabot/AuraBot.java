package dev.aura.aurabot;

import lombok.Getter;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

public class AuraBot {
  @Getter private static JDA JDA;

  public static void main(String args[]) throws Exception {
    JDA = new JDABuilder(AccountType.BOT).setToken(args[0]).build().awaitReady();

    // Start the CommandClient
    JDA.addEventListener(CommandHandler.getClient());

    // Add Listeners Below
    // JDA.addEventListener();

    // Set Presence
    JDA.getPresence().setGame(Game.watching("Code fly past"));
  }
}