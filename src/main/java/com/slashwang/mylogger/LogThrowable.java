package com.slashwang.mylogger;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogThrowable {

    private String mTag;

    public LogThrowable() {
        mTag = Global.logTag;
    }

    public String show(Throwable t) {
        t.printStackTrace();
        return message(t);
    }

    private String message(Throwable t) {
        StringWriter writer = new StringWriter();
        t.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }
}
