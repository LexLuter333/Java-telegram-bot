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
                String[] fullcommand = messageText.split(" ");
                String command = fullcommand[0];
                sendMessage(update.getMessage().getChatId(), commandHandler.handleCommand(command, update, fullcommand));
            } else {
                sendMessage(update.getMessage().getChatId(), "Пока я вас не понимаю, напишите /help и введите команду.");
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
