package com.slashwang.mylogger;

import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**log写入文件*/
public class Writable {

    protected File logFile;
    private ExecutorService mThreadPool;
    private LogTask mLogTask;

    public Writable() {
        _init();
    }

    private void _init() {
        String rootDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            logFile = new File(rootDir, Global.logFileName);
            MyLogger.logSelf("日志文件创建成功" + logFile.getAbsolutePath());
        }
        mThreadPool = (mThreadPool==null) ? Executors.newFixedThreadPool(1) : mThreadPool;
        mLogTask = (mLogTask==null) ? new LogTask(logFile) : mLogTask;
    }

    protected void write2File(String content) {
        mLogTask.set(content);
        mThreadPool.execute(mLogTask);
    }

    public static class LogTask implements Runnable {
        private String mContent;
        private File logFile;

        public LogTask(File file){
            logFile = file;
        }

        public void set(String content) {
            mContent = TextUtils.isEmpty(content) ? "" : content;
        }

        @Override
        public void run() {
            if (null == logFile){
                MyLogger.logSelf("Can't resolve the log file; maybe you don't have permissions to write_in_card");
                return ;
            }
            BufferedWriter writer = null ;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile,true)));
                writer.write(Global.getTime());
                writer.write("\n\t\t");
                writer.write(mContent);
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
}
