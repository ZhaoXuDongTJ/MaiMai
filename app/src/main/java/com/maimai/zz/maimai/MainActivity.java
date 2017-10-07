package com.maimai.zz.maimai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.maimai.zz.maimai.bombs.BookLibBomb;
import com.maimai.zz.maimai.bombs.StudentInfo;
import com.maimai.zz.maimai.utils.ImgUtils;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class MainActivity extends AppCompatActivity {
    //用户 个人书本上传
    // 控件注册
    private ImageButton openCamera;
    private ImageButton ScanNode;
    private ImageButton cancelIt;
    private ImageButton publishIt;

    private ImageView imageOfBook;
    private EditText edit_scan;
    //  启动照相机
    public static final int TAKE_PHOTO = 1;
    public static final int REQUEST_CODE = 2;
    private Uri imageUri;
    // 上传云端 确认码
    public static int SUCCESD_PICTURE = 0;
    public static int SUCCESD_CODE = 0;
    //sqlite
    private SqlUserBookContribution dbHelper;
    // 存储 lipepal
    private Bitmap liteImageOfBook;
    private String liteScanNodeBook;
    public ImgUtils imgUtils;
    // 共享 存储
    public SharedPreferences pref;
    //Bmob
    File outputImage;
    File BombFileImg;

    String realFilePathUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //按钮
        openCamera = (ImageButton) findViewById(R.id.openCamera);
        ScanNode = (ImageButton) findViewById(R.id.ScanNode);
        cancelIt = (ImageButton) findViewById(R.id.cancelIt);
        publishIt = (ImageButton) findViewById(R.id.publishIt);
        // 图片 二维码输入框
        imageOfBook = (ImageView) findViewById(R.id.imageOfBook);
        edit_scan = (EditText) findViewById(R.id.edit_scan);
        //
        dbHelper = new SqlUserBookContribution(this,"UserBookContribution.db",null,1);

        //拍照
        openCamera.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //File 存储
                outputImage = new File(getExternalCacheDir(),"output_image.jpg");
                try {
                    if(outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(Build.VERSION.SDK_INT>=24){
                    imageUri = FileProvider.getUriForFile(MainActivity.this,"com.example.cameraalbumtest.fileprovider",outputImage);
                }else {
                    imageUri = Uri.fromFile(outputImage);
                }
                // 启动相机
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });
        //打开二维码扫描器
        ZXingLibrary.initDisplayOpinion(this);
        ScanNode.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
//                startActivity(new Intent(MainActivity.this, CaptureActivity.class));
            }
        });
        //
        cancelIt.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //  sqlite   以及 发送 云端
        publishIt.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(SUCCESD_PICTURE==0 && SUCCESD_CODE==0){
                    Toast.makeText(MainActivity.this, "请拍照书的封面和扫描书后面的条形码", Toast.LENGTH_LONG).show();
                }else if(SUCCESD_PICTURE==1 && SUCCESD_CODE==1){
                    // 发送服务器
                    pref = getSharedPreferences("data",MODE_PRIVATE);


                    final BmobFile bmobFile = new BmobFile(new File(realFilePathUtil));
                    bmobFile.upload(new UploadFileListener() {

                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(MainActivity.this, "OK:"+bmobFile.getFileUrl(), Toast.LENGTH_SHORT).show();
                                BookLibBomb bookLibBomb = new BookLibBomb();
                                bookLibBomb.setStudentID(pref.getString("id",""));
                                bookLibBomb.setScanCode(liteScanNodeBook);
                                bookLibBomb.setCover(bmobFile);
                                bookLibBomb.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if(e==null){
                               //             Toast.makeText(MainActivity.this,"创建数据成功：" + s,Toast.LENGTH_SHORT).show();

                                            // 添加贡献值
                                            StudentInfo studentInfo = new StudentInfo();
                                            studentInfo.increment("mouldContribute");

                              //              Toast.makeText(MainActivity.this,"ObjectID"+pref.getString("ObjectID",""),Toast.LENGTH_SHORT).show();

                                            studentInfo.update(pref.getString("ObjectID",""),new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    if(e==null){
                                                        Log.i("bmob","更新成功");
                                                    }else{
                                                        Log.i("bmob","更新失败："+e.getMessage()+","+e.getErrorCode());
                                                    }
                                                }
                                            });


                                        }else{
                                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                        }
                                    }
                                });
                            }else {
                                Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();

                }
            }
        });
}

// 显示图片 e二维码
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                Bitmap bitmap = null;
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    liteImageOfBook = bitmap;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                imageOfBook.setImageBitmap(bitmap);

                try {
                    realFilePathUtil = ImgUtils.saveBitmap(bitmap);
                 //   Toast.makeText(MainActivity.this,"创建地址成功：" + realFilePathUtil,Toast.LENGTH_SHORT).show();
                    Log.i("bmob","创建地址成功："+realFilePathUtil+"");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                SUCCESD_PICTURE =1;
                break;
            case REQUEST_CODE:
                if (null != data) {
                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        return;
                    }
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);
                        edit_scan.setText(result);

                        liteScanNodeBook = result;

                        //   查询 此书 是否 已经存在数据库

                        BmobQuery<BookLibBomb> bmobQuery  = new BmobQuery<BookLibBomb>();
                        bmobQuery.addWhereEqualTo("ScanCode",result);
                        bmobQuery.setLimit(1);
                        bmobQuery.findObjects(new FindListener<BookLibBomb>() {

                            @Override
                            public void done(List<BookLibBomb> list, BmobException e) {
                                if(e == null){
                                    SUCCESD_CODE =1;
                                    if(list.get(0) == null){
                                    }else {
                                        SUCCESD_CODE =0;
                                        Toast.makeText(MainActivity.this,"小Mai有此书了，谢谢你！",Toast.LENGTH_LONG).show();
                                    }
                                }else {

                                }
                            }

                        });


                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                break;
        }

    }



}
