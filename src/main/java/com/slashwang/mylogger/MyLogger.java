package com.slashwang.mylogger;

import android.util.Log;

/**
 * 自定义log库
 */
public class MyLogger {

    private static LogText sLogText;
    private static LogThrowable sLogT;
    private static Writable sWritable;
    private static boolean isInit = false;

    /**Set tag of each MyLogger.*/
    public static void setTag(String tag) {
        Global.logTag = tag;
    }

    /**@see {@code init()} */
    public static void init(boolean debug, String logFileName) {
        init(debug, null, true, logFileName);
    }

    /**
     * Init your own log util.
     * @param debug log type ,debug (by default) or release .
     * @param tag your own log tag. "MyLogger" is the default tag.
     * @param write true if you want to write into local log file; false the otherwise
     * @param logFileName the local log file name.
     * */
    public static void init(boolean debug, String tag, boolean write, String logFileName) {
        if (!isInit) {
            Global.initGlobal(debug,tag,write,logFileName);
            isInit = true;
        }
        if (write) {
            sWritable = new Writable();
        }
    }

    /**
     * Log the common messages from your code.
     * @param content what you want to show.
     * @param level log level with type of integer.
     *              @see {@link LogText.Level}
     * @exception Exception if you use these methods below before the class has been inited,
     *          this method would throw a soft exception to remind you that .
     * */
    public static void log(String content, int level) {
        try {
            sLogText = (sLogText == null) ? new LogText() : sLogText;
            String msg = sLogText.log(content, level);
            if (Global.isWrite
                    && Global.canWrite) {
                sWritable.write2File(msg);
            }
        } catch (Exception e) {
            Log.e(Global.logTag, "MyLogger尚未初始化!");
        }
    }

    public static void log(String content) {
        log(content, LogText.Level.MID);
    }

    /**
     * Log the object of throwable.
     * Since the throwable can print stack trace on its own, this method is simply
     * meant to grab the stack trace information ,and write it down to the local log
     * file.
     * @param t any throwable object
     * */
    public static void t(Throwable t) {
        sLogT = (sLogT == null) ? new LogThrowable() : sLogT;
        String msg = sLogT.show(t);
        if (Global.isWrite
                && Global.canWrite) {
            sWritable.write2File(msg);
        }
    }

    /**This method only used inside this package(mainly for the Writable). DO NOT call it outside ;*/
    static void logSelf(String content) {
        sLogText = (sLogText == null) ? new LogText() : sLogText;
        sLogText.log(content, LogText.Level.LOW);
    }

    public static void canWrite(boolean b) {
        Global.canWrite = b;
    }
}
