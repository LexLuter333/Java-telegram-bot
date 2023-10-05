package org.kirillandrey.service;

import org.kirillandrey.config.BotConfig;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {
    BotConfig config;
    CommandHandler commandHandler;

    public TelegramBot(BotConfig config) {

        this.config = config;
        this.commandHandler = new CommandHandler();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            if (messageText.startsWith("/")) {

                String command = messageText.split(" ")[0]; // Получите команду
                sendMessage(update.getMessage().getChatId(), commandHandler.handleCommand(command, update));
            } else {
                sendMessage(update.getMessage().getChatId(), "While I don't understand you, write /help and enter the command.");
            }
        }
    }

    private void sendMessage(long chatID, String textToSend){
        SendMessage msg = new SendMessage();
        msg.setChatId(String.valueOf(chatID));
        msg.setText(textToSend);
        try{
            execute(msg);
        }
        catch (TelegramApiException e){

        }
    }
    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }
}
