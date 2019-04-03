package dev.aura.aurabot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;

public class EmbedCMD extends Command {
  public EmbedCMD() {
    this.name = "embed";
  }

  @Override
  protected void execute(CommandEvent event) {
    // Staff Check
    if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) return;

    String[] args = event.getArgs().split("~");
    EmbedBuilder embed = new EmbedBuilder();
    embed.setColor(Color.decode("#" + args[0]));
    embed.setTitle(args[1]);
    List<String> argsList = Arrays.asList(args);
    embed.setDescription(
        argsList
            .subList(2, argsList.size())
            .stream()
            .map(s -> s != null ? s : "")
            .collect(Collectors.joining(" \n")));
    embed.setFooter("Aura Development Team", null);

    event.getChannel().sendMessage(embed.build()).queue();
  }
}
