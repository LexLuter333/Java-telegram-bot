package org.kirillandrey;

import org.kirillandrey.alerting.Alert;
import org.kirillandrey.alerting.AlertUtil;
import org.kirillandrey.config.BotConfig;
import org.kirillandrey.service.TelegramBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import static org.kirillandrey.alerting.AlertUtil.signalUserListChanged;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        BotConfig config = new BotConfig();

        TelegramBot bot = new TelegramBot(config);

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        Alert alert = new Alert(bot);
        AlertUtil.setAlert(alert);
        // Создаем новый поток для выполнения Alert
        Thread alertThread = new Thread(() -> {
            // Запускаем периодическое оповещение
            alert.startAlert();
        });
        alertThread.start();

    }
}