package com.maimai.zz.maimai;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.maimai.zz.maimai.bombs.BmobObjectID;
import com.maimai.zz.maimai.bombs.VersionNumber;
import com.maimai.zz.maimai.utils.AppConfig;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Button button =(Button)findViewById(R.id.buttonbangben);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BmobQuery<VersionNumber> query =new BmobQuery<VersionNumber>();
                query.getObject(BmobObjectID.VerSionNumber, new QueryListener<VersionNumber>() {
                    @Override
                    public void done(VersionNumber versionNumber, BmobException e) {
                        if(e==null){
                            String number = versionNumber.getVersionNumber();
                                Toast.makeText(AboutActivity.this,"服务器版本：" + number+" \n "+"本地版本："+AppConfig.versionMai ,Toast.LENGTH_SHORT).show();
                        }else {
                                Toast.makeText(AboutActivity.this,"查询失败：",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
