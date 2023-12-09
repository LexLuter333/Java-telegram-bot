package org.kirillandrey.dialogService;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kirillandrey.config.DBConfig;
import org.kirillandrey.dialogsService.controller.DialogHandler;
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
        // Подготовьте данные в базе данных, чтобы установить начальное состояние чата
        Long chatId1 = 98765L;
        dbHandler.signUpUser(chatId1);
        dbHandler.setState(chatId1, "0");
        String response3 = dialogHandler.handleAnswerDialog("Настройки", chatId1);
        assertEquals("", response3);
        String state3 = dbHandler.getState(chatId1);
        assertEquals("3", state3);


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
    }

    @Test
    public void testHandleAskDialog() {
        Long chatId1 = 124125L;
        dbHandler.signUpUser(chatId1);
        dbHandler.setState(chatId1, "0");
        List<String> button1 = new ArrayList<>();

        String response1 = dialogHandler.handleAskDialog(chatId1, button1);
        assertEquals("Вы находитесь в меню", response1);
        assertEquals(List.of("Узнать погоду", "Настройки"), button1);

        Long chatId2 = 72872L;
        dbHandler.signUpUser(chatId2);
        dbHandler.setState(chatId2, "2");
        List<String> button2 = new ArrayList<>();

        String response2 = dialogHandler.handleAskDialog(chatId2, button2);
        assertEquals("Чтобы посмотреть погоду ещё раз, нажмите \"Узнать погоду\", или вернитесь в \"Меню\".", response2);
        assertEquals(List.of("Узнать погоду", "Меню"), button2);

        Long chatId3 = 84720L;
        dbHandler.signUpUser(chatId3);
        dbHandler.setState(chatId3, "17");
        List<String> button3 = new ArrayList<>();

        String response3 = dialogHandler.handleAskDialog(chatId3, button3);
        assertEquals("Ошибка ввода, вернитесь в \"Меню\"", response3);
        assertEquals(List.of("Меню"), button3);

        Long chatId4 = 2385325L;
        dbHandler.signUpUser(chatId4);
        dbHandler.setState(chatId4, "16");
        List<String> button4 = new ArrayList<>();

        String response4 = dialogHandler.handleAskDialog(chatId4, button4);
        assertEquals("Выберите метод получения локации для прогноза погоды (По городу или по геолокации)", response4);
        assertEquals(List.of("Узнать погоду по городу", "Узнать погоду по геолокации","Меню"), button4);
    }
    @After
    public void clear() throws SQLException {
        dbHandler.clearTest();
    }
}
