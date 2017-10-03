package com.maimai.zz.maimai;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maimai.zz.maimai.bombs.BookLibBomb;
import com.maimai.zz.maimai.litepals.BookLibrary;
import com.maimai.zz.maimai.utils.ImgUtils;

import org.litepal.crud.DataSupport;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

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
    public String sId;
    public String sScanCode;
    public int noNumber;
    public int TotalNumber;
    public int lastNumber;
    private List<BookLibrary> bookLibraries;
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
        BmobQuery<BookLibBomb>query = new BmobQuery<BookLibBomb>();
        query.getObject("aee85c77cf", new QueryListener<BookLibBomb>() {
            @Override
            public void done(BookLibBomb bookLibBomb, BmobException e) {
                int count=1;
                if (e == null){
                    sId = bookLibBomb.getStudentID();
                    sScanCode = bookLibBomb.getScanCode();
                    ShowStudentId.setText("提供者学号: "+sId);
                    ShowScanNode.setText("书号: "+sScanCode);
                    count = 2;
                }else if (count ==1){
                    Toast.makeText(litepalTestActivity.this, "数据库无可书，请添加后再使用此功能", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

        //
        noNumber = 0;
        bookLibraries = DataSupport.findAll(BookLibrary.class);
        TotalNumber = bookLibraries.size();
        lastNumber = TotalNumber-1;


        //  启动  处理;
        // 切换 数据 使用的
        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                switch (noNumber){
                  case 0:
                      noNumber = TotalNumber-1;
                      ShowAll(noNumber);
                      break;
                  default:
                      noNumber--;
                      ShowAll(noNumber);
                      break;
                }
            }
        });

        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(noNumber == lastNumber){
                    noNumber = 0;
                    ShowAll(noNumber);
                }else {
                    noNumber++;
                    ShowAll(noNumber);
                }
            }
        });
        //

    }

    public void ShowAll(int num){
        BookLibrary bookLibrary = bookLibraries.get(num);
        ShowStudentId.setText(bookLibrary.getStudentID());
        ShowScanNode.setText(bookLibrary.getScanCode());
        Bitmap bitmap = ImgUtils.toImg(bookLibrary.getCover()) ;
        ShowUserBook.setImageBitmap(bitmap);

    }

}
