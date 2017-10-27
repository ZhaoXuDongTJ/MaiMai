package com.maimai.zz.maimai;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.maimai.zz.maimai.bombs.StudentInfo;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



public class LoginNewActivity extends AppCompatActivity implements OnClickListener {
    private static final String INFORMATION_PORTAL_URL = "http://my.tjut.edu.cn/index.portal";      //信息门户网站
    private static final String INFORMATION_PORTAL_POST_URL = "http://my.tjut.edu.cn/userPasswordValidate.portal";      //信息门户请求网站
    private static final String INFORMATION_PORTAL_CAPTCHA_URL = "http://my.tjut.edu.cn/captchaGenerate.portal";      //信息门户验证码

    private static final int  LOAD_CAPTCHA_OK = 0;      //下载验证码成功
    private static final int  LOGIN_SUCCESS = 1;      //登录成功
    private static final int  LOGIN_FAIL = 2;      //登录失败
    private static final int GET_INFO_SUCCESS = 3;      //抓取信息成功
//
    private EditText studentIdEdit;
    private EditText passwordEdit;
    private EditText captchaEdit;
    private TextInputLayout username;
    private TextInputLayout passwords;
    private TextInputLayout inputCodes;
    private EditText ScanNodeID;
//
    private String studentId;
    private String password;
    private String captcha;

    private Button signInBtn;
    private ImageView captchaImgView;
//
    private OkHttpClient client;
    private String firstCookie;
    private String lastCookie;
    private String cookie;

    private String responseTitle;
    private Document doc;

   // private User user;
    private StudentInfo studentInfo;
    private String name;
    //
    // 共享 存储
    public SharedPreferences.Editor editor;
    public SharedPreferences pref;
    // 二维码扫描
    private ImageButton ScanNodeLogin;
    public static final int REQUEST_CODE = 4;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LOAD_CAPTCHA_OK:
                    byte[] imgBts = (byte[]) msg.obj;
                    Bitmap captchaBitmap = BitmapFactory.decodeByteArray(imgBts,0,imgBts.length);
                    //设置
                    captchaImgView.setImageBitmap(captchaBitmap);
                    break;
                case LOGIN_SUCCESS:
                    getInfo();
                    break;
                case LOGIN_FAIL:
                    Toast.makeText(LoginNewActivity.this,"账号或密码不正确，请重新输入",Toast.LENGTH_SHORT).show();
                    break;
                case GET_INFO_SUCCESS:
                    Toast.makeText(LoginNewActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    setInfo();
                    break;


            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // bomb 后端 初始化
        Bmob.initialize(this, "7a9a1ebc139655e8ca2c74b2b7c68b25");

        client = new OkHttpClient();

        initView();
        initListener();
        initData();

        //判断本地是否 缓存
        if(pref.getBoolean("State",false) != false){
            Toast.makeText(LoginNewActivity.this, "欢迎回来！"+pref.getString("name",""), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginNewActivity.this,ContextActivity.class);
            startActivity(intent);
            finish();
        }
        //   二维码 扫描 学号;
        ZXingLibrary.initDisplayOpinion(this);
        ScanNodeLogin = (ImageButton) findViewById(R.id.ScanNodeLogin);
        ScanNodeLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginNewActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }



    public void login(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody requestBody = new FormBody.Builder()
                        .add("Login.Token1",studentId)
                        .add("Login.Token2",password)
                        .add("captchaField",captcha)
                        .add("goto","http://my.tjut.edu.cn/loginSuccess.portal")
                        .add("gotoOnFail","http://my.tjut.edu.cn/loginFailure.portal")
                        .build();
                Request request = new Request.Builder()
                        .url(INFORMATION_PORTAL_POST_URL)
                        .addHeader("Cookie",firstCookie)
                        .post(requestBody)
                        .build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message message = handler.obtainMessage();
                if(response != null && response.isSuccessful()) {
                    lastCookie = response.headers("Set-Cookie").get(0).substring(0,response.headers("Set-Cookie").get(0).indexOf(";"));
                    cookie = firstCookie + ";" + lastCookie ;
                    message.what = LOGIN_SUCCESS;
                }
                handler.sendMessage(message);
            }
        }).start();
    }


//切换验证码
    public void getCaptchaImg() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder().url(INFORMATION_PORTAL_CAPTCHA_URL).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        firstCookie = response.headers("Set-Cookie").get(0).substring(0,response.headers("Set-Cookie").get(0).indexOf(";"));
                        //获取验证码图片
                        byte[] bytes = response.body().bytes();
                        Message message = handler.obtainMessage();
                        message.what = LOAD_CAPTCHA_OK;
                        message.obj = bytes;
                        handler.sendMessage(message);
                    }
                });
            }
        }).start();

    }

//  一个验证
    public boolean checkInfo(String studentId,String password,String captcha){
        if(studentId.isEmpty()||password.isEmpty()||captcha.isEmpty()){
            Toast.makeText(this,"学号或密码或验证码不能为空！",Toast.LENGTH_SHORT).show();
            return false;
        }else if(studentId.length() != 8){
            Toast.makeText(this,"学号长度必须为6！",Toast.LENGTH_SHORT).show();
            return false;
        }else if(password.length() <6 ){
            Toast.makeText(this,"密码长度必须大于6！",Toast.LENGTH_SHORT).show();
            return false;
        }else if(captcha.length() !=4 ){
            Toast.makeText(this,"验证码长度必须为4！",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }


    public void getInfo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
//                Log.d(TAG, "run: -----------------------------------3");
                try {
                    doc = Jsoup.connect(INFORMATION_PORTAL_URL).cookie("Cookie",cookie).post();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                responseTitle = doc.select("title").first().text();
                //需要爬虫== name
                name = doc.select("#pf221 > div > div.portletContent > table > tbody > tr > td:nth-child(2) > div > ul > li:nth-child(1)").toString().substring(5,9);



                Message message = handler.obtainMessage();
//                Log.d(TAG, "run: -----------------------------------4");
                if("欢迎访问信息门户".equals(responseTitle)){
                    message.what = GET_INFO_SUCCESS;
                    //需要的代码
                    editor.putString("id", studentId);
                    editor.putString("name", name);
                    editor.putBoolean("State", true);
                    editor.commit();
                    BmobQuery<StudentInfo> query = new BmobQuery<StudentInfo>();
                    query.addWhereEqualTo("studentID",studentId);
                    query.setLimit(1);
                    query.findObjects(new FindListener<StudentInfo>() {
                        @Override
                        public void done(List<StudentInfo> list, BmobException e) {
                            final List<StudentInfo> lists = list;
                            if(e!=null){
                                StudentInfo studentInfo = new StudentInfo();
                                studentInfo.setStudentID(studentId);
                                studentInfo.setPassword(password);
                                studentInfo.setUserName(name);
                                studentInfo.setMouldContribute(0);
                                studentInfo.setBlockNumber(0);
                                studentInfo.setRoomNumber(0);
                                studentInfo.setBookBuy(0);
                                studentInfo.setBookSell(0);
                                studentInfo.setWifiContribute(0);
                                studentInfo.setWifiIncome(0);
                                studentInfo.setDeliverGood(0);
                                studentInfo.setReceiptGood(0);
                                studentInfo.setMember(false);
                                studentInfo.setHasWiFIpassword(false);
                                studentInfo.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if(e == null){
                                            editor.putBoolean("State", true);
                                            editor.putString("ObjectID",lists.get(0).getObjectId());
                                            editor.commit();
                                            Toast.makeText(LoginNewActivity.this, "登录成功！" + name, Toast.LENGTH_LONG).show();
                                        }else {
                                            Toast.makeText(LoginNewActivity.this, "网络可能开小差！", Toast.LENGTH_LONG).show();

                                        }
                                    }
                                });
                            }else {
                                editor.putString("ObjectID",list.get(0).getObjectId());
                                editor.commit();
                            }
                        }
                    });

//                    Toast.makeText(LoginNewActivity.this, "登录成功！" + name, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginNewActivity.this,ContextActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    message.what = LOGIN_FAIL;
                }
                handler.sendMessage(message);
            }
        }).start();
    }

    public void setInfo(){
        studentInfo = new StudentInfo();
        studentInfo.setUserName(name);
        studentInfo.setStudentID(studentId);
        studentInfo.setPassword(password);
    }

    public void initView(){

      //  studentIdEdit = (EditText) findViewById(R.id.id);
        //passwordEdit = (EditText) findViewById(R.id.password);
        captchaEdit = (EditText)findViewById(R.id.editText);
        captchaImgView = (ImageView)findViewById(R.id.codes);
        signInBtn = (Button) findViewById(R.id.student_sign);

        username = (TextInputLayout) findViewById(R.id.id);
        passwords = (TextInputLayout) findViewById(R.id.password);
        inputCodes = (TextInputLayout) findViewById(R.id.inputCodes);
        ScanNodeID = (EditText)findViewById(R.id.ScanNodeID);

        // add test  data
        editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        pref = getSharedPreferences("data",MODE_PRIVATE);

        //配置 WiFi公社
        editor.putString("wifiInfoObjID","wifiInfo");
        editor.commit();
    }

    public void initListener(){
        captchaImgView.setOnClickListener(this);
        signInBtn.setOnClickListener(this);
    }

    public void initData(){

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.codes:
                getCaptchaImg();
                break;
            case R.id.student_sign:
                //  此处修改
                studentId = username.getEditText().getText().toString();
                password = passwords.getEditText().getText().toString();
                captcha = inputCodes.getEditText().getText().toString();
                if(checkInfo(studentId,password,captcha)){
                    login();
                }
                break;
        }
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
                        ScanNodeID.setText(result);
                        Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        Toast.makeText(LoginNewActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                break;
        }
    }

}

