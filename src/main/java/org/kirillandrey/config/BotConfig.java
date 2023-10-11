package org.kirillandrey.config;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BotConfig {
    String botName;
    String botToken;

    public BotConfig () {
        Dotenv dotenv = Dotenv.load();
        this.botName = dotenv.get("botName");
        this.botToken = dotenv.get("botToken");

    }
    public String getBotName(){
        return this.botName;
    }
    public String getBotToken(){
        return this.botToken;
    }
}
