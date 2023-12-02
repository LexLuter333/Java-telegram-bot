package org.kirillandrey.advice;

import java.util.HashMap;

public class HumidityAdvice {

    private static HashMap<Integer, String> mapHumAdvice = new HashMap<>();

    public HumidityAdvice(){
        mapHumAdvice.put(30 , "- Сегодня низкая влажность воздуха, пейте больше воды.");
        mapHumAdvice.put(60 , "- Сегодня стандартный уровень влажности, ваш день должен быть отличным.");
        mapHumAdvice.put(100 , "- Сегодня высокая влажность воздуха, пейте больше воды.");

    }
    public String humAdvice(Integer hum){
        if (hum < 30){
            return mapHumAdvice.get(30);
        } else if (hum > 60 ){
            return mapHumAdvice.get(100);
        } else if (hum > 30 && hum < 60){
            return mapHumAdvice.get(60);
        } else {
            return "";
        }
    }
}
