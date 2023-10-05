package org.kirillandrey.config;

import java.util.Scanner;

public class BotConfig {
    String botName;
    String botToken;

    public BotConfig (){
        Scanner input = new Scanner(System.in);
        /*System.out.println("Write name Telegram Bot: ");
        this.botName = input.nextLine();
        System.out.println("Write token Telegram Bot: ");
        this.botToken = input.nextLine();*/
        this.botName = "ASD712";
        this.botToken = "6244660860:AAGE0o45qJREqOFCV3tCQQyST6MP_Wfi6t8";
    }
    public String getBotName(){
        return this.botName;
    }
    public String getBotToken(){
        return this.botToken;
    }
}
