package com.dayoung.ginseng.message.domain;

public enum MessageRequestStatus {
    REQUEST("R"), ACCEPT("A");

    private MessageRequestStatus(final String shortStatus){
        this.shortStatus = shortStatus;
    }

    private String shortStatus;

    public String getShortStatus() {
        return shortStatus;
    }
}
