package com.maimai.zz.maimai;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.maimai.zz.maimai.utils.ImgUtils;

public class litepalTestActivity extends AppCompatActivity {
    //
    private ImageView ShowUserBook;
    private TextView ShowScanNode;
    private TextView ShowStudentId;
    //
    private Button buttonUp;
    private Button buttonDown;
    //
    // 连接数据库 lipepal
    //记录 书的序列
    public int noNumber;
    public int TotalNumber;
    public int lastNumber;
    // imgeUtils
    private ImgUtils imgUtils;
    // litepal



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litepal_test);
        //
        ShowUserBook = (ImageView) findViewById(R.id.ShowUserBook);
        ShowScanNode = (TextView) findViewById(R.id.ShowScanNode);
        ShowStudentId = (TextView) findViewById(R.id.ShowStudentId);
        buttonUp = (Button) findViewById(R.id.buttonUp);
        buttonDown = (Button) findViewById(R.id.buttonDown);
        //




    }



}
