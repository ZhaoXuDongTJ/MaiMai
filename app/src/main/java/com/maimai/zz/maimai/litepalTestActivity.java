package com.maimai.zz.maimai;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.maimai.zz.maimai.litepals.BookLibrary;
import com.maimai.zz.maimai.utils.ImgUtils;

import org.litepal.crud.DataSupport;

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
    BookLibrary bookLibrarie;
    // imgeUtils
    private ImgUtils imgUtils;


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
        noNumber = 0;
        bookLibrarie = DataSupport.find(BookLibrary.class,noNumber);
        ShowAll(0,bookLibrarie);
//        TotalNumber = bookLibraries.size();
//        lastNumber = TotalNumber-1;

        //  启动  处理;
//        switch (TotalNumber){
//            case 0:
//                Toast.makeText(litepalTestActivity.this, "数据库无可书，请添加后再使用此功能", Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                ShowAll(0);
//                break;
//        }
        // 切换 数据 使用的
        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                switch (noNumber){
                  case 0:
                      noNumber = TotalNumber-1;
                      ShowAll(noNumber,bookLibrarie);
                      break;
                  default:
                      noNumber--;
                      ShowAll(noNumber,bookLibrarie);
                      break;
                }
            }
        });

        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(noNumber == lastNumber){
                    noNumber = 0;
                    ShowAll(noNumber,bookLibrarie);
                }else {
                    noNumber++;
                    ShowAll(noNumber,bookLibrarie);
                }
            }
        });
        //

    }

    public void ShowAll(int num,BookLibrary bookLibrary){
//        ShowStudentId.setText(bookLibrary.getStudentID());
//        ShowScanNode.setText(bookLibrary.getScanCode());
//        Bitmap bitmap = ImgUtils.toImg(bookLibrary.getCover()) ;
//        ShowUserBook.setImageBitmap(bitmap);

        ShowStudentId.setText("sd");
        ShowScanNode.setText("sdsd");
    }

}
