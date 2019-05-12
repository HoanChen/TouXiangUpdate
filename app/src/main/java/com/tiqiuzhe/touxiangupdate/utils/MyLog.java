package com.tiqiuzhe.touxiangupdate.utils;

import android.util.Log;

/**
 * Created by qiangchen on 2017/11/14.
 * email:2511525208@qq.com
 * desc:关闭日志
 */

public class MyLog {

//    public static int LOG_LEVEL = 0;//上线模式
    public static int LOG_LEVEL = 6;//开发模式
    public static int ERROR = 1;
    public static int WARN = 2;
    public static int INFO = 3;
    public static int DEBUG = 4;
    public static int VERBOS = 5;

    //可以全局控制是否打印log日志
    private static boolean isPrintLog = true;
    private static int LOG_MAXLENGTH = 2000;


//    public static void e(String tag,String msg){
//        if(LOG_LEVEL>ERROR)
//            Log.e(tag, msg);
//    }
//
//    public static void w(String tag,String msg){
//        if(LOG_LEVEL>WARN)
//            Log.w(tag, msg);
//    }
//    public static void i(String tag,String msg){
//        if(LOG_LEVEL>INFO)
//            Log.i(tag, msg);
//    }
//    public static void d(String tag,String msg){
//        if(LOG_LEVEL>DEBUG)
//            Log.d(tag, msg);
//    }
//    public static void v(String tag,String msg){
//        if(LOG_LEVEL>VERBOS)
//            Log.v(tag, msg);
//    }

    public static void e(String tagName, String msg) {
        if (LOG_LEVEL>VERBOS) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.e(tagName + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.e(tagName + i, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

    public static void w(String tagName, String msg) {
        if (LOG_LEVEL>VERBOS) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.w(tagName + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.w(tagName + i, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

    /**
     * 其中LENGTH是自己设置的长度值,一般为4000(下面有原因为啥是这个值).
       还很有理有据的解释,底层Logger有长度限制是4*1024,都不知道验证一下就发出了,结果这个方法的确可以打印很多log了.
       但是,但是!  log换行打印之后,两行之间会丢失不少字符!
     * */
    public static void i(String tagName, String msg) {
        if (LOG_LEVEL>VERBOS) {
            //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
            //  把4*1024的MAX字节打印长度改为2001字符数
            int max_str_length = 2001 - tagName.length();
            //大于4000时
            while (msg.length() > max_str_length) {
                Log.i(tagName, msg.substring(0, max_str_length));
                msg = msg.substring(max_str_length);
            }
            //剩余部分
            Log.i(tagName, msg);
        }
    }

//    public static void i(String tagName, String msg) {
//        if (LOG_LEVEL>VERBOS) {
//            int strLength = msg.length();
//            int start = 0;
//            int end = LOG_MAXLENGTH;
//            for (int i = 0; i < 100; i++) {
//                if (strLength > end) {
//                    Log.i(tagName + i, msg.substring(start, end));
//                    start = end;
//                    end = end + LOG_MAXLENGTH;
//                } else {
//                    Log.i(tagName + i, msg.substring(start, strLength));
//                    break;
//                }
//            }
//        }
//    }

    public static void d(String tagName, String msg) {
        if (LOG_LEVEL>VERBOS) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.d(tagName + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.d(tagName + i, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

    public static void v(String tagName, String msg) {
        if (LOG_LEVEL>VERBOS) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.v(tagName + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.v(tagName + i, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }



}
