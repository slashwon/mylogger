package com.slashwang.mylogger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Global {

    private static SimpleDateFormat sFormat;

    public static String logFileName = ".defaultlog";

    public static String logTag = "MyLogger";

    public static boolean isDebug = true;

    public static boolean isWrite = true;
    public static boolean canWrite = false;

    public static void initGlobal(boolean debug, String tag, boolean write, String logFileName) {
        Global.logFileName = logFileName;
        Global.logTag = tag;
        Global.isDebug = debug;
        Global.isWrite = write;
    }

    public static String getTime() {
        sFormat = (null == sFormat) ? new SimpleDateFormat("yy-MM-dd HH:mm:ss") : sFormat;
        return sFormat.format(new Date());
    }
}
