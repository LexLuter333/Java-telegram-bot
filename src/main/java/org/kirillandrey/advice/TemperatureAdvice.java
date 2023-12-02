package org.kirillandrey.advice;

import java.util.HashMap;

public class TemperatureAdvice {
    private static HashMap<String, String> mapTempAdvice = new HashMap<>();

    public TemperatureAdvice(){
        mapTempAdvice.put("030" , "- Сегодня будет жарко, не забывайте надеть головной убор и пейте больше жидкости.");
        mapTempAdvice.put("020" , "- Сегодня хорошая прогулка для прогулки.");
        mapTempAdvice.put("010" , "- Сегодня будет прохладно, советуем надеть ветровку.");
        mapTempAdvice.put("0" , "- Сегодня прохладно и возможен гололёд, советуем надеть шапку.");
        mapTempAdvice.put("110" , "- Сегодня прохладно, но если одеться теплее, то это хорошая погода для прогулки.");
        mapTempAdvice.put("120" , "- Сегодня холодно, советуем одеться теплее.");
        mapTempAdvice.put("130" , "- Сегодня очень холодно, советуем очень тепло одеться.");

    }
    public String tempAdvice(Integer countTemp){
        if (countTemp == null) {
            return "";
        }
        String result;
        if (countTemp % 10 != 0 && countTemp != 0){
            countTemp = countTemp - (countTemp % 10);
        }

        if (countTemp > 0){
            result = "0" + countTemp;
        } else if (countTemp < 0) {
            result = "1" + Math.abs(countTemp);
        } else {
            result = String.valueOf(countTemp);
        }
        return mapTempAdvice.get(result);
    }
}
