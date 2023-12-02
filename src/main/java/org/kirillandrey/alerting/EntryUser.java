package org.kirillandrey.alerting;

public class EntryUser {
    private String chatid;
    private String time;

    public EntryUser(String chatid, String time){
        this.chatid = chatid;
        this.time = time;
    }

    public String getChatid() {
        return chatid;
    }

    public String getTime() {
        return time;
    }

    public void setChatid(String chatid) {
        this.chatid = chatid;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
