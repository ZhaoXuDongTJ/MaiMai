package com.maimai.zz.maimai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maimai.zz.maimai.bombs.BookLibBomb;
import com.maimai.zz.maimai.bombs.StudentInfo;
import com.maimai.zz.maimai.utils.ImgUtils;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class SearchActivity extends AppCompatActivity {
//   UI
    private TextView textView;
    private ImageView imageView;
    private String filePath;
    private Button Sbutton1,Sbutton2;
//
    private String ObjectID;
//
    private String StudentID;
    // 共享 存储
    public SharedPreferences.Editor editor;
    public SharedPreferences pref;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        String ScanNode = intent.getStringExtra("Scan");
        textView = (TextView) findViewById(R.id.ImgPicScanNode);
        imageView = (ImageView) findViewById(R.id.ImgPicBook);
        Sbutton1 = (Button) findViewById(R.id.Sbutton1);
        Sbutton2 = (Button) findViewById(R.id.Sbutton2);
        textView.setText("书号 : "+ScanNode);
        // add test  data
        editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        pref = getSharedPreferences("data",MODE_PRIVATE);

        StudentID = pref.getString("ObjectID","");

        BmobQuery<BookLibBomb> bmobQuery  = new BmobQuery<BookLibBomb>();
        bmobQuery.addWhereEqualTo("ScanCode",ScanNode);
        bmobQuery.setLimit(1);
        bmobQuery.findObjects(new FindListener<BookLibBomb>() {
            @Override
            public void done(List<BookLibBomb> list, BmobException e) {
                if(e==null){
                    for (BookLibBomb gameScore : list) {
                        //  找到 图片 开始 下载这个图片 保存到本地
                        ObjectID = gameScore.getObjectId();
                        BmobFile bmobfile = gameScore.getCover();
                        if(bmobfile!= null){
                            filePath = ImgUtils.FilePath();  // 本地 地址
                            File saveFile = new File(filePath);
                            //调用bmobfile.download方法
                            bmobfile.download(saveFile, new DownloadFileListener() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if(e==null){
                                         // Toast.makeText(SearchActivity.this,"下载成功,保存路径:"+s,Toast.LENGTH_SHORT).show();
                                        Bitmap bitmap = ImgUtils.parseBitMap(filePath);                                // 得到 bitmap  对象
                                        imageView.setImageBitmap(bitmap);                                             //  设置 imageview的图片显示  就是 转化一下
                                    }else{
                                        Toast.makeText(SearchActivity.this,"下载失败："+e.getErrorCode()+","+e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onProgress(Integer integer, long l) {

                                }
                            });
                        }else{
                            Toast.makeText(SearchActivity.this,"还没有这本书呢！发布一套模板把！",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }
            }
        });

        Sbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookLibBomb bookLibBomb = new BookLibBomb();
                bookLibBomb.increment("seller");
                bookLibBomb.update(ObjectID, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        Toast.makeText(SearchActivity.this,"有小伙伴买走第一时间通知你！",Toast.LENGTH_SHORT).show();
                    }
                });

                StudentInfo studentInfo = new StudentInfo();
                studentInfo.increment("bookSell");
                studentInfo.increment("deliverGood");
                studentInfo.update(StudentID, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {

                    }
                });

            }
        });

        Sbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BmobQuery<BookLibBomb> query = new BmobQuery<BookLibBomb>();
                query.getObject(ObjectID, new QueryListener<BookLibBomb>() {
                    @Override
                    public void done(BookLibBomb bookLibBomba, BmobException e) {
                        if(null==e){
                            if(bookLibBomba.getSeller()>0){
                                BookLibBomb bookLibBomb = new BookLibBomb();
                                bookLibBomb.increment("buyer");
                                bookLibBomb.update(ObjectID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        Toast.makeText(SearchActivity.this,"拿回家把！",Toast.LENGTH_SHORT).show();
                                    }
                                });

                                StudentInfo studentInfo = new StudentInfo();
                                studentInfo.increment("bookBuy");
                                studentInfo.increment("receiptGood");
                                studentInfo.update(StudentID, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                            }else {
                                Toast.makeText(SearchActivity.this,"还没小伙伴提供！再等等！",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

    }
}
