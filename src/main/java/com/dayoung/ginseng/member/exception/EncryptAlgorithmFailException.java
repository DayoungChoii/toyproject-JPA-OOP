package com.dayoung.ginseng.member.exception;

public class EncryptAlgorithmFailException extends RuntimeException{
    public EncryptAlgorithmFailException(){};
    public EncryptAlgorithmFailException(String message){
        super(message);
    }
    public EncryptAlgorithmFailException(Throwable cause) {
        super(cause);
    }

    public EncryptAlgorithmFailException(String message, Throwable cause) {
        super(message, cause);
    }
}
