package dev.aura.aurabot;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import dev.aura.aurabot.commands.EmbedCMD;

public class CommandHandler {
  public static CommandClient getClient() {
    return new CommandClientBuilder()
        .setOwnerId("158653179041546242")
        .setPrefix(":a")
        .addCommand(new EmbedCMD())
        .build();
  }
}
