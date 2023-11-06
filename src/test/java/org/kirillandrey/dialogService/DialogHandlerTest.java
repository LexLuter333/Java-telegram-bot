package org.kirillandrey.dialogService;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.kirillandrey.dialogsService.controller.DialogHandler;
import org.kirillandrey.service.DateBaseHandler;

import java.util.ArrayList;
import java.util.List;

public class DialogHandlerTest {
    private DialogHandler dialogHandler;

    @Before
    public void setUp() {
        dialogHandler = new DialogHandler();
    }

    @Test
    public void testHandleAnswerDialog() {
        // Подготовьте данные в базе данных, чтобы установить начальное состояние чата
        DateBaseHandler dbHandler = new DateBaseHandler();
        Long chatId = 12345L;
        dbHandler.signUpUser("John", "Doe", chatId);
        dbHandler.setState(chatId, "1");

        // Теперь мы можем протестировать метод handleAnswerDialog
        String response1 = dialogHandler.handleAnswerDialog("назад", chatId);
        assertEquals("", response1);
        String state1 = dbHandler.getState(chatId);
        assertEquals("0", state1);

        String response2 = dialogHandler.handleAnswerDialog("asdad", chatId);
        assertEquals("", response2);
        String state2 = dbHandler.getState(chatId);
        assertEquals("3", state2);
    }

    @Test
    public void testHandleAskDialog() {

        DateBaseHandler dbHandler = new DateBaseHandler();

        Long chatId1 = 54321L;
        dbHandler.signUpUser("Alice", "Smith", chatId1);
        dbHandler.setState(chatId1, "0");
        List<String> button1 = new ArrayList<>();

        String response1 = dialogHandler.handleAskDialog(chatId1, button1);
        assertEquals("Вы находитесь в меню", response1);
        assertEquals(List.of("Узнать погоду", "Настройки"), button1);

        Long chatId2 = 72872L;
        dbHandler.signUpUser("Flin", "Greck", chatId2);
        dbHandler.setState(chatId1, "2");
        List<String> button2 = new ArrayList<>();

        String response2 = dialogHandler.handleAskDialog(chatId1, button2);
        assertEquals("Чтобы посмотреть погоду ещё раз, нажмите \"Узнать погоду\" или вернитесь в \"Меню\".", response2);
        assertEquals(List.of("Узнать погоду", "Меню"), button2);

        Long chatId3 = 84720L;
        dbHandler.signUpUser("Jhon", "Felix", chatId3);
        dbHandler.setState(chatId1, "3");
        List<String> button3 = new ArrayList<>();

        String response3 = dialogHandler.handleAskDialog(chatId1, button3);
        assertEquals("Ошибка ввода, вернитесь в \"Меню\"", response3);
        assertEquals(List.of("Меню"), button3);
    }
}
