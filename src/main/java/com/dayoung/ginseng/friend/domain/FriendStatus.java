package com.dayoung.ginseng.friend.domain;

public enum FriendStatus {
    REQUEST("R"), ACCEPT("A");

    private FriendStatus(final String shortStatus){
        this.shortStatus = shortStatus;
    }

    private String shortStatus;

    public String getShortStatus() {
        return shortStatus;
    }
}
