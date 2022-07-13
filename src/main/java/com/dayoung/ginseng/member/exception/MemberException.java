package com.dayoung.ginseng.member.exception;

public class MemberException extends RuntimeException{
    public MemberException(){};
    public MemberException(String message){
        super(message);
    }
    public MemberException(Throwable cause) {
        super(cause);
    }

    public MemberException(String message, Throwable cause) {
        super(message, cause);
    }
}
