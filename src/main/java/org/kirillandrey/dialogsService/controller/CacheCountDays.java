package org.kirillandrey.dialogsService.controller;

import java.util.HashMap;

public class CacheCountDays {
    static private HashMap<Long, Integer> count = new HashMap<>();
    public  static Integer getDays(Long chatid){
        if (count.containsKey(chatid) && chatid != null){
            Integer resault = count.get(chatid);
            count.remove(chatid);
            return resault;
        }
        else {
            return null;
        }
    }
    public static void addDays(Long chatid, Integer days){
        if (chatid != null && days != null && days > 0) {
            if (days <= 5) {
                count.put(chatid, days);
            } else count.put(chatid, 5);
        }
    }
}
