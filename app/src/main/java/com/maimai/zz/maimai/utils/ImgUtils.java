package com.maimai.zz.maimai.utils;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.maimai.zz.maimai.litepals.BookLibrary;

import java.io.ByteArrayOutputStream;

/**
 * Created by 92198 on 2017/10/2.
 */

public class ImgUtils {

    // 共享 存储    获得 学号
    public SharedPreferences pref;

    // 连接 共享 存储
    public  void connectSP(SharedPreferences pref){
        this.pref = pref;
    }

    // 存储到数据库
    public BookLibrary toLite(Bitmap bitmap, String edit_scan){
        // lipepal  对象
        BookLibrary bookLibrary;
        bookLibrary = new BookLibrary();
        bookLibrary.setStudentID(pref.getString("StudentID",""));
        bookLibrary.setScanCode(edit_scan);
        bookLibrary.setCover(toByte(bitmap));
        bookLibrary.save();

        return bookLibrary;

    }



    //  图片 转化 字节
    public static byte[] toByte(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
    //字节 转化 图片
    public static Bitmap toImg(byte[] images){
        return BitmapFactory.decodeByteArray(images,0,images.length);
    }

    public static Byte[] parseByte(byte[] bytes){
        Byte[] It = new Byte[bytes.length];

        int i=0;
        for(byte b  : bytes){
            It[i++] = b;
        }

        return It;
    }

    public static byte[] parsebyte(Byte[] bytes){
        byte[] It = new byte[bytes.length];

        int i=0;
        for(Byte b  : bytes){
            It[i++] = b;
        }

        return It;
    }

}
