package com.yc.exception;

/**
 * 创建一个三层异常体系
 */
class MiddleException extends BaseException{
    public MiddleException(){}
    public MiddleException(String message){
        super(message);
    }
}
class BottomException extends MiddleException{
    public BottomException(){}
    public BottomException(String message){
        super(message);
    }
}
public class BaseException extends Exception{
    public BaseException(){}
    public BaseException(String message){
        super(message);
    }
}
