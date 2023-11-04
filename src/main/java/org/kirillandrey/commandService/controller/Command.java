package org.kirillandrey.commandService.controller;


public interface Command {
    String execute(Long chatid, String[] args);
    String getInfo();

}