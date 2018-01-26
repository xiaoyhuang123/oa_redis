package com.ft.backend.work.client.common;

import java.io.Serializable;

/**
 * Created by huanghongyi on 2016/11/8.
 */
public class ReturnResult<T> implements Serializable{

    private static final long serialVersionUID = 1019129305089727015L;

    public ReturnResult(){}
    public ReturnResult(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public ReturnResult(ResultCode resultCode){
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public ReturnResult(ResultCode resultCode, T t){
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public static <T> ReturnResult<T> getNewSuccessedInstance(){
        return new ReturnResult<>(ResultCode.SUCCESS);
    }

    public static <T> ReturnResult<T> getNewFailedInstance(){
        return new ReturnResult<>(ResultCode.FAILED);
    }

    public static <T> ReturnResult<T> getNewFailedInstance(int code, String msg, T result){

        ReturnResult<T> resutnResult = new ReturnResult<>(ResultCode.FAILED);
        resutnResult.setCode(code);
        resutnResult.setMsg(msg);
        resutnResult.setResult(result);
        return resutnResult;
    }

    public static <T> ReturnResult<T> getNewSuccessedInstance(T result){
        ReturnResult<T> resutnResult = new ReturnResult<>(ResultCode.SUCCESS);
        resutnResult.setResult(result);
        return resutnResult;
    }

    private int code;

    private String msg;

    private T result;

    public boolean isSuccessed(){
        return getCode() == ResultCode.SUCCESS.code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ReturnResult [code=").append(code).append(", msg=").append(msg).append(", result=")
                .append(result).append("]");
        return builder.toString();
    }
}
