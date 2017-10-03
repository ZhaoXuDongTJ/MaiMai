package com.maimai.zz.maimai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import cn.bmob.v3.Bmob;

/**
 * A login screen that offers login via studentId/password.
 */
public class LoginActivity extends AppCompatActivity{

    // UI references.
    private EditText mStudentIdView;
    private EditText mPasswordView;
    private Button mStudent_sign;
    private TextView testRequestResponse;
    //字符串
    private String username;
    private String password;
    // 共享 存储
    public SharedPreferences.Editor editor;
    public SharedPreferences pref;
    // 二维码扫描
    private ImageButton ScanNodeLogin;
    public static final int REQUEST_CODE = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // bomb 后端 初始化
        Bmob.initialize(this, "7a9a1ebc139655e8ca2c74b2b7c68b25");

        init();
        editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        pref = getSharedPreferences("data",MODE_PRIVATE);
        //
        if(pref.getBoolean("State",false) == false){
            editor.putString("StudentID","20162434");
            editor.putString("password","000000");
            editor.putBoolean("Stata",false);
            editor.apply();
        }else {
            Toast.makeText(LoginActivity.this, "欢迎回来！", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this,ContextActivity.class);
            startActivity(intent);
            finish();
        }


        mStudent_sign.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                username = mStudentIdView.getText().toString();
                password = mPasswordView.getText().toString();
                String sql_StudentID = pref.getString("StudentID","");
                String sql_password = pref.getString("password","");
                if(username.isEmpty()||password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "学号或密码不可以空", Toast.LENGTH_LONG).show();
                }else if(username.length() == 8){
                    if(username.equals(sql_StudentID) && password.equals(sql_password)){
                        Toast.makeText(LoginActivity.this, "认证成功", Toast.LENGTH_LONG).show();
                        editor.putBoolean("State",true);
                        editor.apply();
                        finish();
                        Intent intent = new Intent(LoginActivity.this,ContextActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginActivity.this, "认证失败,请检查账号密码", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    //   二维码 扫描 学号;
        ZXingLibrary.initDisplayOpinion(this);
        ScanNodeLogin = (ImageButton) findViewById(R.id.ScanNodeLogin);
        ScanNodeLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_CODE:
                if (null != data) {
                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        return;
                    }
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);
                        mStudentIdView.setText(result);
                        Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        Toast.makeText(LoginActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                break;
        }
    }


    public void init(){
        // Set up the login form.
        mStudentIdView =(EditText) findViewById(R.id.studentId);
        mPasswordView =(EditText) findViewById(R.id.password);
        mStudent_sign =(Button) findViewById(R.id.student_sign);
        testRequestResponse = (TextView)findViewById(R.id.testRequestResponse);

        // add test  data

    }


}





















