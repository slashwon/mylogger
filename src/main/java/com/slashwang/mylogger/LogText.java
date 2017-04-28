package com.slashwang.mylogger;

import android.text.TextUtils;
import android.util.Log;

/**
 * 日志内容
 */
public class LogText {

    /**
     * log级别
     */
    public interface Level {
        int LOW = 100;
        int MID = 200;
        int HIGH = 300;
    }

    private final String mTag;

    protected static final String HEADER = "***************************** START *****************************";

    protected static final String FOOT = "============================== END ===========================";

    public LogText() {
        mTag = Global.logTag;
    }

    public String log(String content, int level) {
        return set(content, level);
    }

    /**
     * 获取log标签，默认值为MyLogger
     */
    private String getFinalTag() {
        return (TextUtils.isEmpty(mTag)) ? "MyLogger" : mTag;
    }

    private String set(String content, int l) {
        h();
        String msg = setUpContent(content, l);
        f();
        return msg;
    }

    private void f() {
        Log.e(getFinalTag(), FOOT);
    }

    /**
     * 设置打印内容，格式为 所属类 所在方法 行号 自定义log语句
     */
    private String setUpContent(String content, int l) {
        content = (TextUtils.isEmpty(content)) ? "" : content;
        StackTraceElement stackTrace = getStackTraceElement();
        StringBuilder msg = new StringBuilder();

        if (stackTrace != null) {
            msg.append("AT\t");
            msg.append(stackTrace.getClassName());
            msg.append(".");
            msg.append(stackTrace.getMethodName());
            msg.append("()");
            msg.append("\t");
            msg.append("Line Number: ");
            msg.append(stackTrace.getLineNumber());
            msg.append("\n\t\t");
            msg.append(content);
        }

        String t = getFinalTag();
        String text = msg.toString();
        // 如果不是debug模式，直接返回log串不打印
        if (!Global.isDebug)
            return text;

        switch (l) {
            case Level.LOW:
                Log.i(t, text);
                break;
            case Level.MID:
                Log.w(t, text);
                break;
            case Level.HIGH:
            default:
                Log.e(t, text);
                break;
        }

        return text;
    }

    private StackTraceElement getStackTraceElement() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        boolean shouldTrace = false;
        StackTraceElement targetStackTrace = null;
        for (StackTraceElement ste : stackTrace) {
            boolean isLogMethod = ste.getClassName().equals(MyLogger.class.getName());
            if (shouldTrace && !isLogMethod) {
                targetStackTrace = ste;
                break;
            }
            shouldTrace = isLogMethod;
        }
        return targetStackTrace;
    }

    private void h() {
        Log.e(getFinalTag(), HEADER);
    }
}
