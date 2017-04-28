package com.slashwang.mylogger;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**log写入文件*/
public class Writable {

    protected File logFile;

    public Writable() {
        _init();
    }

    private void _init() {
        logFile = new File(Environment.getExternalStorageDirectory(),Global.logFileName);
        MyLogger.logSelf("日志文件创建成功"+logFile.getAbsolutePath());
    }

    protected void write2File(String content) {
        if (!logFile.exists()){
            MyLogger.logSelf("Can't resolve the log file; maybe you don't have permissions to write_in_card");
            return ;
        }
        BufferedWriter writer = null ;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile,true)));
            writer.write(Global.getTime());
            writer.write("\n\t\t");
            writer.write(content);
            writer.write("\n");
            writer.flush();
            MyLogger.logSelf("写入文件成功");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer!=null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
