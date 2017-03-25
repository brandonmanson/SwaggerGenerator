package com.brandonmanson.models;

import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by brandonmanson on 3/22/17.
 */
@Component
public class SlackResponse {

    private boolean ok;
    private HashMap<String, HashMap<String, String>> file;
    private String error;

    public SlackResponse(){};

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public HashMap<String, HashMap<String, String>> getFile() {
        return file;
    }

    public void setFile(HashMap<String, HashMap<String, String>> file) {
        this.file = file;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
