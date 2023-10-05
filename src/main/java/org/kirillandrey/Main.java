package org.kirillandrey;

import org.kirillandrey.config.BotConfig;
import org.kirillandrey.service.TelegramBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) throws TelegramApiException {

        BotConfig config = new BotConfig();

        TelegramBot bot = new TelegramBot(config);
        //System.out.println(bot.getBotToken());
        //System.out.println(bot.getBotUsername());

        //System.setProperty("log4j.configuration", "C\\Users\\Андрей\\Desktop\\Java-telegram-bot\\log4j.xml");

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            //e.printStackTrace();
        }
    }
}