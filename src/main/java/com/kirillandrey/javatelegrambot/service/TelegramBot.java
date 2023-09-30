package com.kirillandrey.javatelegrambot.service;

import com.kirillandrey.javatelegrambot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;
    public TelegramBot(BotConfig config) { this.config = config; }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){
            String msgText = update.getMessage().getText();
            long chatID = update.getMessage().getChatId();

            switch (msgText){
                case "/start":
                    startCommandReceived(chatID, update.getMessage().getChat().getFirstName());
                    break;
                default: sendMessage(chatID, "sorry, command was not supported(");

            }
        }
    }

    private void startCommandReceived(long chatID, String firstName) {
        String ans = "Hi, " + firstName + " , nice to meet you!";
        sendMessage(chatID, ans);
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
        return config.getbotName();
    }

    @Override
    public String getBotToken() {
        return config.getbotToken();
    }
}
