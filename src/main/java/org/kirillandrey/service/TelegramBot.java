package org.kirillandrey.service;

import org.kirillandrey.config.BotConfig;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TelegramBot extends TelegramLongPollingBot {
    BotConfig config;
    CommandHandler commandHandler;
    DialogHandler dialogHandler;

    public TelegramBot(BotConfig config) {

        this.config = config;
        this.commandHandler = new CommandHandler();
        this.dialogHandler = new DialogHandler();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            List<String> button = new ArrayList<>();

            if (messageText.startsWith("/")) {
                String[] fullcommand = messageText.split(" ");
                String command = fullcommand[0];
                try {
                    sendMessage(update.getMessage().getChatId(), commandHandler.handleCommand(command, update, fullcommand), button);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                sendMessage(update.getMessage().getChatId(), dialogHandler.handleDialog(messageText, update, button), button);
            }
        }
    }

    private void sendMessage(long chatID, String textToSend, List<String> button){
        SendMessage msg = new SendMessage();
        msg.setChatId(String.valueOf(chatID));
        msg.setText(textToSend);
        msg.setReplyMarkup(createKeyboard(button));
        try{
            execute(msg);
        }
        catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
    private ReplyKeyboardMarkup createKeyboard(List<String> button) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        for (String label : button) {
            KeyboardRow row = new KeyboardRow();
            row.add(label);
            keyboard.add(row);
        }

        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
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
