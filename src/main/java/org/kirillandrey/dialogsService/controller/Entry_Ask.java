package org.kirillandrey.dialogsService.controller;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Entry_Ask {
    private String m_ask;
    private List<String> button;

    public Entry_Ask() {
        m_ask = "";
        button = new ArrayList<>();
    }

    public void setM_ask(String m_ask) {
        this.m_ask = m_ask;
    }

    public void setButton(List<String> button) {
        this.button = new ArrayList<>(button);
    }

    public String getM_ask() {
        return m_ask;
    }

    public List<String> getButton() {
        return Collections.unmodifiableList(button);
    }
}
