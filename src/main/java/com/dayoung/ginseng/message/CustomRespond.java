package com.dayoung.ginseng.message;

public enum CustomRespond {
    SUCCESS("S"), FAIL("F");

    private CustomRespond(final String shortStatus){
        this.shortStatus = shortStatus;
    }

    private String shortStatus;

    public String getShortStatus() {
        return shortStatus;
    }
}
