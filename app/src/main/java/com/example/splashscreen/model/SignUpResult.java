package com.example.splashscreen.model;

import com.example.splashscreen.apiHelper.Record;

public class SignUpResult {

    private boolean success;
    private Record record;
    private String token;

    public SignUpResult() {}

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
