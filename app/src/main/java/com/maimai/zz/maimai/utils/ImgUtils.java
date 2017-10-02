package com.maimai.zz.maimai.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by 92198 on 2017/10/2.
 */

public class ImgUtils {
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

}
