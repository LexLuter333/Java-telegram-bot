package org.kirillandrey.advice;

import java.util.HashMap;

public class HumidityAdvice {

    private static HashMap<String, String> mapHumAdvice = new HashMap<>();

    private void addHashMap(){
        mapHumAdvice.put("30" , "Сегодня низкая влажность воздуха, пейте больше воды.");
        mapHumAdvice.put("60" , "Сегодня стандартный уровень влажности, ваш день должен бфть отличным.");
        mapHumAdvice.put("100" , "Сегодня высокая влажность воздуха, пейте больше воды.");

    }
    public static String HumAdvice(long hum){
        String result;

        if (hum < 30){
            return mapHumAdvice.get(30);
        } else if (hum > 60 ){
            return mapHumAdvice.get(100);
        } else if (hum > 30 && hum < 60){
            return mapHumAdvice.get(60);
        } else {
            return "ERROR";
        }


    }
}
