package org.kirillandrey.dialogService;

import org.junit.jupiter.api.Test;
import org.kirillandrey.dialogsService.controller.Entry_Ask;
import org.kirillandrey.dialogsService.dialogs.ChooseMethod;

import static org.junit.jupiter.api.Assertions.*;

public class ChooseMethodTest {

    @Test
    public void testAsk() {
            ChooseMethod chooseMethod = new ChooseMethod();

        Entry_Ask entryAsk = chooseMethod.ask(123L);

        assertEquals("Выберите метод получения локации для прогноза погоды (По городу или по геолокации)", entryAsk.getM_ask());

        assertEquals(3, entryAsk.getButton().size());
        assertTrue(entryAsk.getButton().contains("Узнать погоду по городу"));
        assertTrue(entryAsk.getButton().contains("Узнать погоду по геолокации"));
        assertTrue(entryAsk.getButton().contains("Меню"));
    }

    @Test
    public void testAnswer() {
        ChooseMethod chooseMethod = new ChooseMethod();
        String result = chooseMethod.answer("Some message", 123L);
        assertEquals("", result);
    }

    @Test
    public void testGetKey() {
        ChooseMethod chooseMethod = new ChooseMethod();
        String key = chooseMethod.getKey();
        assertNull(key);
    }
}