package com.github.auradevelopment.aurabot;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;

public class CommandHandler {
    public static CommandClient getClient(){
        return new CommandClientBuilder()
                .setOwnerId("158653179041546242")
                .setPrefix(":a ")
                .build();
    }
}
