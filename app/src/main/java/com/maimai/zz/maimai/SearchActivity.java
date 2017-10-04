package com.maimai.zz.maimai;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maimai.zz.maimai.bombs.BookLibBomb;
import com.maimai.zz.maimai.utils.ImgUtils;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;

public class SearchActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        String ScanNode = intent.getStringExtra("Scan");
        textView = (TextView) findViewById(R.id.ImgPicScanNode);
        imageView = (ImageView) findViewById(R.id.ImgPicBook);
        textView.setText(ScanNode);

        BmobQuery<BookLibBomb> bmobQuery  = new BmobQuery<BookLibBomb>();
        bmobQuery.addWhereEqualTo("ScanCode",ScanNode);
        bmobQuery.setLimit(1);
        bmobQuery.findObjects(new FindListener<BookLibBomb>() {
            @Override
            public void done(List<BookLibBomb> list, BmobException e) {
                if(e==null){
                    for (BookLibBomb gameScore : list) {
                        BmobFile bmobfile = gameScore.getCover();
                        if(bmobfile!= null){
                            filePath = ImgUtils.FilePath();
                            File saveFile = new File(filePath);
                            //调用bmobfile.download方法
                            bmobfile.download(saveFile, new DownloadFileListener() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if(e==null){
                                        Toast.makeText(SearchActivity.this,"下载成功,保存路径:"+s,Toast.LENGTH_SHORT).show();
                                        Bitmap bitmap = ImgUtils.parseBitMap(filePath);
                                        imageView.setImageBitmap(bitmap);

                                    }else{
                                        Toast.makeText(SearchActivity.this,"下载失败："+e.getErrorCode()+","+e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onProgress(Integer integer, long l) {

                                }
                            });
                        }
                    }
                }else{

                }
            }
        });



    }
}
