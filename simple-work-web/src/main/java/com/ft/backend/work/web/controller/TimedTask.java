package com.ft.backend.work.web.controller;

/**
 * Created by huanghongyi on 2017/2/24.
 */
public class TimedTask {

    private static int index=0;
    /**
     * 业务逻辑处理
     */
    public void execute() {
        // 执行业务逻辑
        // ........
        System.out.println("---------------------------------------");
        System.out.println("---------定时任务......."+index+"------- ");
        System.out.println("---------------------------------------");
        index++;
    }
}
