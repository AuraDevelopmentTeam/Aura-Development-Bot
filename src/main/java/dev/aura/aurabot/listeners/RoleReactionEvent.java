package dev.aura.aurabot.listeners;

import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

@Log4j2
public class RoleReactionEvent extends ListenerAdapter {
  public void onMessageReactionAdd(MessageReactionAddEvent event) {
    // Assign the reaction channel
    TextChannel ch = event.getJDA().getTextChannelById("561650523691024385");

    if (event.getUser().isBot()) return;
    if (event.getTextChannel() != ch) return;

    logger.debug("Processing reaction added...");

    logger.debug("\tBungeeChat");
    if (event.getReactionEmote().getIdLong() == 561652702338220043L) {
      Role role = event.getJDA().getRoleById(535231407237365771L);
      if (event.getMember().getRoles().contains(role)) return;
      // Doesn't contain role, carry on
      event.getGuild().getController().addRolesToMember(event.getMember(), role).queue();

      // Send confirmation msg
      event
          .getUser()
          .openPrivateChannel()
          .queue(
              channel ->
                  channel
                      .sendMessage(
                          "You will now be notified for announcements regarding **BungeeChat**.")
                      .queue());

      logger.info("Giving " + event.getMember() + " BungeeChat role.");
    }

    logger.debug("\tPowerMoney");
    if (event.getReactionEmote().getIdLong() == 561652518568853536L) {
      Role role = event.getJDA().getRoleById(535232083258245120L);
      if (event.getMember().getRoles().contains(role)) return;
      // Doesn't contain role, carry on
      event.getGuild().getController().addRolesToMember(event.getMember(), role).queue();

      // Send confirmation msg
      event
          .getUser()
          .openPrivateChannel()
          .queue(
              channel ->
                  channel
                      .sendMessage(
                          "You will now be notified for announcements regarding **PowerMoney**.")
                      .queue());

      logger.info("Giving " + event.getMember() + " PowerMoney role.");
    }
  }

  public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
    // Assign the reaction channel
    TextChannel ch = event.getJDA().getTextChannelById("561650523691024385");

    if (event.getUser().isBot()) return;
    if (event.getTextChannel() != ch) return;

    logger.debug("Processing reaction removed...");

    logger.debug("\tBungeeChat");
    if (event.getReactionEmote().getIdLong() == 561652702338220043L) {
      Role role = event.getJDA().getRoleById(535231407237365771L);
      if (!event.getMember().getRoles().contains(role)) return;
      // Contain role, carry on
      event.getGuild().getController().addRolesToMember(event.getMember(), role).queue();

      // Send confirmation msg
      event
          .getUser()
          .openPrivateChannel()
          .queue(
              channel ->
                  channel
                      .sendMessage(
                          "You will no longer be notified for announcements regarding **BungeeChat**.")
                      .queue());

      logger.info("Removing " + event.getMember() + " BungeeChat role.");
    }

    logger.debug("\tPowerMoney");
    if (event.getReactionEmote().getIdLong() == 561652518568853536L) {
      Role role = event.getJDA().getRoleById(535232083258245120L);
      if (!event.getMember().getRoles().contains(role)) return;
      // Contains role, carry on
      event.getGuild().getController().addRolesToMember(event.getMember(), role).queue();

      // Send confirmation msg
      event
          .getUser()
          .openPrivateChannel()
          .queue(
              channel ->
                  channel
                      .sendMessage(
                          "You will no longer be notified for announcements regarding **PowerMoney**.")
                      .queue());

      logger.info("Removing " + event.getMember() + " PowerMoney role.");
    }
  }
}
