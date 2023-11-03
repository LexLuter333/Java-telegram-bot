package org.kirillandrey.dialogsService.dialogs;

import org.kirillandrey.weatherBot.WeatherJsonParser;
import org.kirillandrey.service.DateBaseHandler;
import org.kirillandrey.dialogsService.controller.Dialog;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WCity implements Dialog {
    String s_Ask = "Введите название города:";
    String s_Answer = "";
    List<String> keyboard = new ArrayList<>();
    @Override
    public String ask() {
        return s_Ask;
    }

    @Override
    public String answer(String message, Update update, List<String> button) {
        return s_Answer;
    }
}
