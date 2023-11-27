package org.kirillandrey.advice;

import java.util.ArrayList;
import java.util.HashMap;

public class WeatherAdvice {

    private static HashMap<String, String> mapWethAdvice = new HashMap<>();

    private void addHashMap(){
        mapWethAdvice.put("Rain", "Сегодня возможен дождь советуем взять зонтик.");
        mapWethAdvice.put("Snow", "Сегодня будет снег, будьте аккуратнее.");
        mapWethAdvice.put("Clouds", "");
        mapWethAdvice.put("Clear", "");
    }

    public static String WeathAdvice(ArrayList<String> weth){
        if (weth.contains("Rain")){
            return mapWethAdvice.get("Rain");
        } else if(weth.contains("Snow")){
            return mapWethAdvice.get("Snow");
        } else if(weth.contains("Clouds")){
            return mapWethAdvice.get("Clouds");
        } else if (weth.contains("Clear")) {
            return mapWethAdvice.get("Clear");
        } else {
            return "";
        }
    }
}
