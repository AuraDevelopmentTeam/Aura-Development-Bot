package dev.aura.aurabot.listeners;

import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

@Log4j2
public class RoleReactionEvent extends ListenerAdapter {
  public void onMessageReactionAdd(MessageReactionAddEvent event) {
    // Assign the reaction channel
    TextChannel ch = event.getJDA().getTextChannelById("561650523691024385");

    if (event.getUser().isBot()) return;
    if (event.getTextChannel() != ch) return;

    logger.debug("Begin Reaction Checks");
  }
}
