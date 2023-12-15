package org.kirillandrey.dialogService;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kirillandrey.config.DBConfig;
import org.kirillandrey.dialogsService.controller.CacheCountDays;
import org.kirillandrey.dialogsService.controller.DialogHandler;
import org.kirillandrey.dialogsService.controller.Entry_Ask;
import org.kirillandrey.service.DBConfigTest;
import org.kirillandrey.service.DateBaseHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DialogHandlerTest {
    private DBConfig dbConfig = new DBConfigTest();
    private DateBaseHandler dbHandler = new DateBaseHandler(dbConfig);
    private DialogHandler dialogHandler = new DialogHandler(dbHandler);
    @Test
    public void testHandleAnswerDialog() {
        Long chatId = 12345L;
        dbHandler.signUpUser(chatId);
        dbHandler.setState(chatId, "1");

        String response1 = dialogHandler.handleAnswerDialog("назад", chatId);
        assertEquals("", response1);
        String state1 = dbHandler.getState(chatId);
        assertEquals("16", state1);

        String response2 = dialogHandler.handleAnswerDialog("asdad", chatId);
        assertEquals("", response2);
        String state2 = dbHandler.getState(chatId);
        assertEquals("17", state2);

        Long chatId1 = 98765L;
        dbHandler.signUpUser(chatId1);
        dbHandler.setState(chatId1, "0");
        String response3 = dialogHandler.handleAnswerDialog("Настройки", chatId1);
        assertEquals("", response3);
        String state3 = dbHandler.getState(chatId1);
        assertEquals("3", state3);

        CacheCountDays.addDays(chatId, 1);
        String city = "asddasd";
        dbHandler.setState(chatId, "1");
        String response4 = dialogHandler.handleAnswerDialog(city, chatId);
        assertEquals("Не можем найти \""+ city + "\" город. Попробуйте ещё, например: \"Moscow\" или \"Москва\"", response4);
        String state4 = dbHandler.getState(chatId);
        assertEquals("2", state4);
    }

    @Test
    public void testHandleAskDialog() {
        Long chatId1 = 124125L;
        dbHandler.signUpUser(chatId1);
        dbHandler.setState(chatId1, "0");

        Entry_Ask entryAsk1 = dialogHandler.handleAskDialog(chatId1);
        String response1 = entryAsk1.getM_ask();
        List<String> button1 = entryAsk1.getButton();
        assertEquals("Вы находитесь в меню", response1);
        assertEquals(List.of("Узнать погоду", "Настройки"), button1);

        Long chatId2 = 72872L;
        dbHandler.signUpUser(chatId2);
        dbHandler.setState(chatId2, "2");
        List<String> button2 = new ArrayList<>();

        Entry_Ask entryAsk2 = dialogHandler.handleAskDialog(chatId2);
        String relativeString2 = entryAsk2.getM_ask();
        List<String> button2Result = entryAsk2.getButton();
        assertEquals("Чтобы посмотреть погоду ещё раз, нажмите \"Узнать погоду\", или вернитесь в \"Меню\".", relativeString2);
        assertEquals(List.of("Узнать погоду", "Меню"), button2Result);

        Long chatId3 = 84720L;
        dbHandler.signUpUser(chatId3);
        dbHandler.setState(chatId3, "17");
        List<String> button3 = new ArrayList<>();

        Entry_Ask entryAsk3 = dialogHandler.handleAskDialog(chatId3);
        String relativeString3 = entryAsk3.getM_ask();
        List<String> button3Result = entryAsk3.getButton();
        assertEquals("Ошибка ввода, вернитесь в \"Меню\"", relativeString3);
        assertEquals(List.of("Меню"), button3Result);

        Long chatId4 = 2385325L;
        dbHandler.signUpUser(chatId4);
        dbHandler.setState(chatId4, "16");
        List<String> button4 = new ArrayList<>();

        Entry_Ask entryAsk4 = dialogHandler.handleAskDialog(chatId4);
        String relativeString4 = entryAsk4.getM_ask();
        List<String> button4Result = entryAsk4.getButton();
        assertEquals("Выберите метод получения локации для прогноза погоды (По городу или по геолокации)", relativeString4);
        assertEquals(List.of("Узнать погоду по городу", "Узнать погоду по геолокации", "Меню"), button4Result);
    }
    @After
    public void clear() throws SQLException {
        dbHandler.clearTest();
    }
}
