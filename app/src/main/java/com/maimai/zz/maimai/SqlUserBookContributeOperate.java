package com.maimai.zz.maimai;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by 92198 on 2017/10/2.
 */

public class SqlUserBookContributeOperate {

    // 提供人的 信息;
    private SqlUserBookContribution dbhelper;
    private Context context;

    public SqlUserBookContributeOperate(Context context,SqlUserBookContribution dbhelper){
        this.context = context;
        this.dbhelper = dbhelper;
    }

    public void saveImage(){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("id", 1);

        cv.put("BookPicture", bitmabToBytes(context));//图片转为二进制
        db.insert("UserBookContribution", null, cv);
        db.close();
    }

    //图片转为二进制数据
    public byte[] bitmabToBytes(Context context){
        //将图片转化为位图
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.id.imageOfBook);
        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
        //创建一个字节数组输出流,流的大小为size
        ByteArrayOutputStream baos= new ByteArrayOutputStream(size);
        try {
            //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            //将字节数组输出流转化为字节数组byte[]
            byte[] imagedata = baos.toByteArray();
            return imagedata;
        }catch (Exception e){
        }finally {
            try {
                bitmap.recycle();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }
}
