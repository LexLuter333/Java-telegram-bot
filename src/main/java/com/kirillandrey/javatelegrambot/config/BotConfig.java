package com.kirillandrey.javatelegrambot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
public class BotConfig {
    @Value("${name}")
    String botName;
    @Value("${token}")
    String bottoken;

    public String getbotToken() {
        return bottoken;
    }

    public String getbotName() {
        return botName;
    }
}
