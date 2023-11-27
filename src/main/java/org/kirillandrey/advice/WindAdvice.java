package org.kirillandrey.advice;

import java.util.HashMap;

public class WindAdvice {
    private static HashMap<String, String> mapWindAdvice = new HashMap<>();

    private void addHashMap(){
        mapWindAdvice.put("5", "");
        mapWindAdvice.put("12", "Cегодня слиный ветер, будьте аккуратнее.");
        mapWindAdvice.put("24", "Сегодня очень сильный ветер, не берите с собой вещи которые будет тяжело удержать.");
        mapWindAdvice.put("36", "Сегодня ОЧЕНЬ сильный ветер, не советуем выходить из дома.");

    }

    public static String winAdv(double wind){

        if (wind < 5){
            return mapWindAdvice.get(5);
        } else {
            if (wind % 12 != 0){
                wind += (12 - wind % 12);
            }

            return mapWindAdvice.get(wind);
        }

    }
}
