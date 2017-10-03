package com.maimai.zz.maimai.utils;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;



import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

    public static String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

    public static File saveFile(Bitmap bm, String fileName) throws IOException {
        String path = getSDPath() +"/revoeye/";
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        Log.i("bmob","失败："+path+fileName+"");

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();

        return myCaptureFile;
    }

    public static String saveBitmap(Bitmap bitmap) throws IOException
    {
        String filepath = Environment.getExternalStorageDirectory()+"/DCIM/images.jpeg";
        File file = new File(filepath);
        FileOutputStream out;
        try{
            out = new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out))
            {
                out.flush();
                out.close();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return filepath;
    }



}
