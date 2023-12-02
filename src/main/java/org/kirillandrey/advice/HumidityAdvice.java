package org.kirillandrey.advice;

import java.util.HashMap;

public class HumidityAdvice {

    private static final HashMap<Integer, String> mapHumAdvice = new HashMap<>();

    static {
        mapHumAdvice.put(30, "- Сегодня низкая влажность воздуха, пейте больше воды.");
        mapHumAdvice.put(60, "- Сегодня стандартный уровень влажности, ваш день должен быть отличным.");
        mapHumAdvice.put(100, "- Сегодня высокая влажность воздуха, пейте больше воды.");
    }

    public static String humAdvice(Integer hum) {
        if (hum == null) {
            return "";
        }

        if (hum <= 30) {
            return mapHumAdvice.get(30);
        } else if (hum > 30 && hum <= 60) {
            return mapHumAdvice.get(60);
        } else if (hum > 60) {
            return mapHumAdvice.get(100);
        } else {
            return "";
        }
    }
}
