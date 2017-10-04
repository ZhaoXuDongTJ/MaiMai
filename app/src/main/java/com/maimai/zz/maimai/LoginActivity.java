package com.maimai.zz.maimai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.jsoup.nodes.Document;

import cn.bmob.v3.Bmob;

/**
 * A login screen that offers login via studentId/password.
 */
public class LoginActivity extends AppCompatActivity{

    // UI references.
    private TextInputLayout username;
    private TextInputLayout password;
    private TextInputLayout inputCodes;

    private ImageView codes;
    private final String codesUrl = "http://my.tjut.edu.cn/captchaGenerate.portal";
    private Document doc;
    private String usr;
    private String pas;
    private String UserName;
    private String cod;

    private HttpRequester httpRequester=new HttpRequester();

    private Button mStudent_sign;
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

        //判断本地是否 缓存
        if(pref.getBoolean("State",false) != false){
            Toast.makeText(LoginActivity.this, "欢迎回来！"+pref.getString("UserName",""), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this,ContextActivity.class);
            startActivity(intent);
            finish();
        }
        //登入成功 加入 共享内存
        //     editor.putString("StudentID","20162434");
//            editor.putString("password","000000");
//            editor.putBoolean("Stata",false);
//            editor.apply();

//                String sql_StudentID = pref.getString("StudentID","");
//                String sql_password = pref.getString("password","");
        mStudent_sign.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                usr = username.getEditText().getText().toString();
                pas = password.getEditText().getText().toString();
                cod = inputCodes.getEditText().getText().toString();
                login();
//                if(username.isEmpty()||password.isEmpty()){
//                    Toast.makeText(LoginActivity.this, "学号或密码不可以空", Toast.LENGTH_LONG).show();
//                }else if(username.length() == 8){
//                    if(username.equals(sql_StudentID) && password.equals(sql_password)){
//                        Toast.makeText(LoginActivity.this, "认证成功", Toast.LENGTH_LONG).show();
//                        editor.putBoolean("State",true);
//                        editor.apply();
//                        finish();
//                        Intent intent = new Intent(LoginActivity.this,ContextActivity.class);
//                        startActivity(intent);
//                    }else {
//                        Toast.makeText(LoginActivity.this, "认证失败,请检查账号密码", Toast.LENGTH_LONG).show();
//                    }
//                }

//                Intent intent = new Intent(LoginActivity.this,ContextActivity.class);
//                startActivity(intent);


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
//                        mStudentIdView.setText(result);
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

        mStudent_sign =(Button) findViewById(R.id.student_sign);
        username = (TextInputLayout) findViewById(R.id.id);
        password = (TextInputLayout) findViewById(R.id.password);
        inputCodes = (TextInputLayout) findViewById(R.id.inputCodes);

        codes = (ImageView) findViewById(R.id.codes);
        httpRequester.setChoose(false);

        getUrlImage();
        codes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUrlImage();
            }
        });

        // add test  data
        editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        pref = getSharedPreferences("data",MODE_PRIVATE);
    }

    private Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (msg.obj == null)
                        Toast.makeText(LoginActivity.this, "获取验证码失败", Toast.LENGTH_LONG).show();
                        codes.setImageBitmap((Bitmap) msg.obj);
                    break;
                case 1:
                    doc = (Document) msg.obj;
                    if (!doc.getElementsByClass("userinfo").first().text().equals("您好！")) {
                        synchronized (this) {
                            UserName = doc.getElementsByClass("userinfo").first().text();
                            UserName = UserName.replace("您好！", "");
                            UserName = UserName.replace("，", "");
                            UserName = UserName.replace("\n", "");

                            editor.putString("id", usr);
                            editor.putString("name", UserName);
                            editor.putBoolean("State", true);
                            notifyAll();
                            editor.commit();
                            Toast.makeText(LoginActivity.this, "登录成功！" + UserName+"。正在更新数据", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    };

    private void login() {
        if (!usr.equals("")) {
            username.setErrorEnabled(false);
            if (!pas.equals("")) {
                password.setErrorEnabled(false);
                if (!cod.equals("")) {
                    inputCodes.setErrorEnabled(false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            synchronized (this) {
                                httpRequester.post("http://ssfw.tjut.edu.cn/ssfw/j_spring_ids_security_check","j_username="+usr
                                        +"&j_password="+pas
                                        +"&validateCode="+cod,false);
                              //  Document doc = httpRequester.get("http://my.tjut.edu.cn/");
                                Document doc = httpRequester.get("http://ssfw.tjut.edu.cn/ssfw/index.do");
                                Message msg = new Message();
                                msg.what = 1;
                                msg.obj = doc;

                                Log.i("TAG",doc.toString());

                                handle.sendMessage(msg);
                            }
                        }
                    }).start();
                } else {
                    inputCodes.setErrorEnabled(true);
                    inputCodes.setError("请输入验证码");
                }
            } else {
                password.setErrorEnabled(true);
                password.setError("请输入密码");
            }
        } else {
            username.setErrorEnabled(true);
            username.setError("请输入学号");
        }
    }

    private void getUrlImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap image = httpRequester.getImage("http://ssfw.tjut.edu.cn/ssfw/jwcaptcha.do");
                Message msg = new Message();
                msg.what = 0;
                msg.obj = image;
                handle.sendMessage(msg);
            }
        }).start();
    }

}





















