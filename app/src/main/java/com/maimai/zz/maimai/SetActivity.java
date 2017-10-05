package com.maimai.zz.maimai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.maimai.zz.maimai.bombs.StudentInfo;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class SetActivity extends AppCompatActivity {
    private String xuehao;
    private String louhao;
    private String sushehao;
    public SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        TextView sxuehao = (TextView)findViewById(R.id.textViewx);
        final EditText slou = (EditText)findViewById(R.id.editTextl);
        final EditText sshu = (EditText)findViewById(R.id.editTexts);
        pref = getSharedPreferences("data",MODE_PRIVATE);
        Button button = (Button) findViewById(R.id.buttont);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                louhao = slou.getText().toString();         //获取楼号
                sushehao = sshu.getText().toString();      //获取宿舍号
                if (louhao.isEmpty()==true||sushehao.isEmpty()==true){
                    Toast.makeText(SetActivity.this,"学号，楼号，宿舍号不能为空",Toast.LENGTH_SHORT).show();
                }
                else{
                    StudentInfo studentInfo = new StudentInfo();
                    studentInfo.setBlockNumber(Integer.valueOf(louhao));
                    studentInfo.setRoomNumber(Integer.valueOf(sushehao));
                    studentInfo.update(pref.getString("ObjectID",""), new UpdateListener() { /////
                        @Override
                        public void done(BmobException e) {
                            if (e == null){
                                Toast.makeText(SetActivity.this,"设置成功",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SetActivity.this,ContextActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(SetActivity.this,"设置失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }
}
