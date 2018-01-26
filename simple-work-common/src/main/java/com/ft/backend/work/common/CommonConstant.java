package com.ft.backend.work.common;

/**
 * Created by huanghongyi on 2017/11/3.
 */
public class CommonConstant {

    /*********************操作类型****************************************/
    public static  int OP_TYPE_ADD=1;
    public static  int OP_TYPE_DELETE=1;
    public static  int OP_TYPE_UPDATE=1;


    /**************************worker******************************/
    public static String WORKER_TYPE_QIANTOUREN="10";
    public static String WORKER_TYPE_FUZEREN="100";
    public static String WORKER_TYPE_LUOSHIREN="110";


    /**************************File******************************/
    public static int FILE_TYPE_TASK_FILE = 1;
    public static int FILE_TYPE_FEEDBACK_FILE = 2;

    public static int ROLE_SUPER_MANAGER=1;
    public static int ROLE_CHEJIAN_MANAGER=2;
    public static int ROLE_CHEDUI_MANAGER=3;
    public static int ROLE_NORMAL_USER=4;



    public static int SHOW_LEVEL_WORKER=1;
    public static int SHOW_LEVEL_WORKER_ZHIDAOZU=2;
    public static int SHOW_LEVEL_WORKER_CHEDUI=3;
    public static int SHOW_LEVEL_WORKER_CHEDUI_CHEJIAN=4;
}
