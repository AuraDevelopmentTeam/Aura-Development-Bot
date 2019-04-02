package dev.aura.aurabot;

import dev.aura.aurabot.listeners.RoleReactionEvent;
import java.util.HashMap;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Role;
import org.apache.commons.cli.CommandLine;

@Log4j2
public class AuraBot {
  @Getter private static JDA JDA;
  @Getter private static HashMap<Long, Role> assignableRoles;

  public static void main(CommandLine commandLine) throws Exception {
    JDA =
        new JDABuilder(AccountType.BOT)
            .setToken(commandLine.getOptionValue(BotMain.OPT_TOKEN))
            .build()
            .awaitReady();

    assignableRoles = new HashMap<>();
    assignableRoles.put(561652702338220043L, JDA.getRoleById(535231407237365771L)); // BungeeChat
    assignableRoles.put(561652518568853536L, JDA.getRoleById(535232083258245120L)); // PowerMoney

    logger.debug("Starting the CommandClient");
    JDA.addEventListener(CommandHandler.getClient());

    // Add Listeners Below
    JDA.addEventListener(new RoleReactionEvent());

    logger.debug("Setting Presence");
    JDA.getPresence().setGame(Game.watching("Code fly past"));
  }
}
