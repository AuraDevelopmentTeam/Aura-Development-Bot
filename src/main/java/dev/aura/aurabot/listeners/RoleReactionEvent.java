package dev.aura.aurabot.listeners;

import dev.aura.aurabot.AuraBot;
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

    if (AuraBot.getAssignableRoles().containsKey(event.getReactionEmote().getIdLong())) {
      Role role = AuraBot.getAssignableRoles().get(event.getReactionEmote().getIdLong());
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
                          "You will now be notified for announcements regarding **"
                              + role.getName()
                              + "**.")
                      .queue());
    }
  }

  public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
    // Assign the reaction channel
    TextChannel ch = event.getJDA().getTextChannelById("561650523691024385");

    if (event.getUser().isBot()) return;
    if (event.getTextChannel() != ch) return;

    if (AuraBot.getAssignableRoles().containsKey(event.getReactionEmote().getIdLong())) {
      Role role = AuraBot.getAssignableRoles().get(event.getReactionEmote().getIdLong());
      if (event.getMember().getRoles().contains(role)) return;
      // Doesn't contain role, carry on
      event.getGuild().getController().removeRolesFromMember(event.getMember(), role).queue();

      // Send confirmation msg
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
    }
  }
}
