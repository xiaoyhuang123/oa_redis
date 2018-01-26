package com.ft.backend.work.client.common;

/**
 * Created by huanghongyi on 2016/11/8.
 */
public enum ResultCode {

    SYSTEM_ERROR(-2, "系统内部错误"),
    FAILED(-1, "操作失败，请刷新后重试"),
    SUCCESS(0, "操作成功"),
    ILLEGAL_ARGUMENT(1, "非法的请求参数"),
    NO_PERMISSION(2, "无权操作"),
    No_RECORD_FOUND(3,"无查询记录"),
    REMOTE_INVOKE_ERP_INTERFACE_ERROR(4, "调用ERP接口错误，请稍后重试！"),
    PIN_NOT_EXIST(5,"用户pin不存在！"),

//user
    USER_USERNAME_NULL(100,"用户名为空"),
    USER_PASSWORD_NULL(101,"密码为空"),
    USER_PASSWORD_ERROR(102,"用户名或密码不正确"),



    //task
    TASK_QIANTOUREN_NULL(1000,"操作失败，牵头人不允许为空"),
    TASK_FUZEREN_NULL(1001,"操作失败，负责人不允许为空"),

    TASK_NOT_LEADER_TO_DELETE(1101,"操作失败，您只能删除牵头人为当前用户的任务."),


    //user
    WORKER_NO_ALREADY_EXISTS(2000,"工号已存在,请重新输入!"),

    WORKER_CHEJIAN_IS_NULL(2001,"车间领导，所属车间不允许为空!"),
    WORKER_CHEDUI_IS_NULL(2002,"车队领导，所属车间、车队不允许为空!"),







    UN_KNOW(999, "未知异常");

    ResultCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int code;

    public String msg;

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


}
