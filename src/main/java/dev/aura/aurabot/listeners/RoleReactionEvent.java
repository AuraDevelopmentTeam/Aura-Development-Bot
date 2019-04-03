package dev.aura.aurabot.listeners;

import dev.aura.aurabot.AuraBot;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

@Log4j2
public class RoleReactionEvent extends ListenerAdapter {
  private static final String guildName = "Aura Development Team";
  private static final String channelName = "assign-a-role";

  private final TextChannel assignChannel;

  public RoleReactionEvent(JDA JDA) {
    logger.debug("Get the reaction channel");
    assignChannel =
        JDA.getTextChannelsByName(channelName, false)
            .stream()
            .filter(channel -> guildName.equals(channel.getGuild().getName()))
            .findFirst()
            .orElseThrow(
                () ->
                    new IllegalStateException(
                        "Could not find channel \"#"
                            + channelName
                            + "\" on guild \""
                            + guildName
                            + "\"."));
  }

  public void onMessageReactionAdd(MessageReactionAddEvent event) {
    if ((event.getTextChannel() != assignChannel) || (event.getUser().isBot())) return;

    logger.debug("Processing reaction added...");
    logger.debug("\tMember: {}", event.getMember().getEffectiveName());

    if (AuraBot.getAssignableRoles().containsKey(event.getReactionEmote().getIdLong())) {
      Role role = AuraBot.getAssignableRoles().get(event.getReactionEmote().getIdLong());
      logger.debug("\tRole: {}", role.getName());

      if (event.getMember().getRoles().contains(role)) {
        logger.trace("\tMember already has role!");
        return;
      }

      event.getGuild().getController().addRolesToMember(event.getMember(), role).queue();

      logger.debug("\tSending confirmation msg...");
      event
          .getUser()
          .openPrivateChannel()
          .queue(
              channel ->
                  channel
                      .sendMessage(
                          "You will now be notified for announcements regarding **"
                              + role.getName()
                              + "**.")
                      .queue());

      logger.info(
          "Assigned the role {} to member {}.",
          role.getName(),
          event.getMember().getEffectiveName());
    } else {
      // Don't want to output any debug to prevent spam. But just a normal reaction.
    }
  }

  public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
    if ((event.getTextChannel() != assignChannel) || (event.getUser().isBot())) return;

    logger.debug("Processing reaction removed...");
    logger.debug("\tMember: {}", event.getMember().getEffectiveName());

    if (AuraBot.getAssignableRoles().containsKey(event.getReactionEmote().getIdLong())) {
      final Role role = AuraBot.getAssignableRoles().get(event.getReactionEmote().getIdLong());
      logger.debug("\tRole: {}", role.getName());

      if (!event.getMember().getRoles().contains(role)) {
        logger.trace("\tMember doesn't have role!");
        return;
      }

      event.getGuild().getController().removeRolesFromMember(event.getMember(), role).queue();

      logger.debug("\tSending confirmation msg...");
      event
          .getUser()
          .openPrivateChannel()
          .queue(
              channel ->
                  channel
                      .sendMessage(
                          "You will no longer be notified for announcements regarding **"
                              + role.getName()
                              + "**.")
                      .queue());

      logger.info(
          "Removed the role {} from member {}.",
          role.getName(),
          event.getMember().getEffectiveName());
    } else {
      // Don't want to output any debug to prevent spam. But just a normal reaction.
    }
  }
}
