package org.kirillandrey.service;

import org.kirillandrey.JSON.Sys;
import org.kirillandrey.commandService.controller.CommandHandler;
import org.kirillandrey.config.BotConfig;
import org.kirillandrey.dialogsService.controller.DialogHandler;
import org.kirillandrey.dialogsService.controller.Entry_Ask;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
/**
 * Класс {@code TelegramBot} представляет собой телеграм-бот, обрабатывающий сообщения и команды от пользователей.
 */
public class TelegramBot extends TelegramLongPollingBot {
    private BotConfig config;
    private CommandHandler commandHandler;
    private DialogHandler dialogHandler;

    /**
     * Конструктор класса {@code TelegramBot}.
     *
     * @param config конфигурация бота
     */
    public TelegramBot(BotConfig config) {

        this.config = config;
        this.commandHandler = new CommandHandler();
        this.dialogHandler = new DialogHandler();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && (update.getMessage().hasText()) || update.getMessage().hasLocation()) {
            String messageText = "";
            if (update.getMessage().hasText()){
                messageText = update.getMessage().getText();
            } else if (update.getMessage().hasLocation()) {
                Location location = update.getMessage().getLocation();
                messageText = location.getLatitude() + " " + location.getLongitude();
            }
            Long chatid = update.getMessage().getChatId();
            List<String> button = new ArrayList<>();

            if (messageText.startsWith("/")) {
                String[] fullcommand = messageText.split(" ");
                String command = fullcommand[0];
                try {
                    sendMessage(chatid, commandHandler.handleCommand(chatid, command, fullcommand));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                String answer = dialogHandler.handleAnswerDialog(messageText, chatid);
                if (!answer.isEmpty()){
                    sendMessage(chatid, answer);
                }
                Entry_Ask ask = dialogHandler.handleAskDialog(chatid);
                if (!ask.getM_ask().isEmpty()){
                    sendMessage(update.getMessage().getChatId(), ask.getM_ask(), ask.getButton());
                }
            }
        }
    }

    /**
     * Отправляет сообщение пользователю.
     *
     * @param chatID      идентификатор чата пользователя
     * @param textToSend  текст сообщения
     * @param button      список кнопок для клавиатуры
     */
    public void sendMessage(long chatID, String textToSend, List<String> button){

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
    /**
     * Отправляет сообщение пользователю.
     *
     * @param chatID      идентификатор чата пользователя
     * @param textToSend  текст сообщения
     */
    public void sendMessage(long chatID, String textToSend){

        SendMessage msg = new SendMessage();
        msg.setChatId(String.valueOf(chatID));
        msg.setText(textToSend);
        try{
            execute(msg);
        }
        catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    /**
     * Создает клавиатуру для сообщения.
     *
     * @param button список кнопок
     * @return объект клавиатуры
     */
    private ReplyKeyboardMarkup createKeyboard(List<String> button) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        for (String label : button) {
            KeyboardRow row = new KeyboardRow();
            if (label.equals("Отправить геолокацию")){
                KeyboardButton locationButton = new KeyboardButton("Отправить геолокацию");
                locationButton.setRequestLocation(true);
                row.add(locationButton);
                keyboard.add(row);
                continue;
            }
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
