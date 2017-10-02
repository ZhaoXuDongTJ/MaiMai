package com.maimai.zz.maimai;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.maimai.zz.maimai.fragment.FragmentOne;
import com.maimai.zz.maimai.fragment.FragmentThree;
import com.maimai.zz.maimai.fragment.FragmentTwo;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

public class ContextActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private FragmentOne fragmentOne;
    private FragmentTwo fragmentTwo;
    private FragmentThree fragmentThree;
    private Fragment from ;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    ActionBar actionBar;

    //二维码 标识码
    public static final int REQUEST_CODE = 3;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchFragment(from,fragmentOne);
                    actionBar.setTitle("主页");
                    return true;
                case R.id.navigation_dashboard:
                    switchFragment(from,fragmentTwo);
                    actionBar.setTitle("WIFI公社");
                    return true;
                case R.id.navigation_notifications:
                    switchFragment(from,fragmentThree);
                    actionBar.setTitle("个人信息");
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context);



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initView();
        initListner();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,fragmentOne).commit();
        from = fragmentOne;

        //设置标题栏
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.pop_nav);
        actionBar.setTitle("主页");



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(navigationView,true);
            }
        });

    }


    public void switchFragment(Fragment from,Fragment to) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(from!=null && to!=null) {
            if(to.isAdded()){
                fragmentTransaction.hide(from).show(to).commit();
            }else {
                fragmentTransaction.hide(from).add(R.id.fragment_container,to).commit();
            }
            this.from = to;
        }
    }

    public void initView() {
        fragmentOne = new FragmentOne();
        fragmentTwo = new FragmentTwo();
        fragmentThree = new FragmentThree();

        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        navigationView = (NavigationView)findViewById(R.id.navView);

    }


    public void initListner() {
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scanCode:
                Toast.makeText(this,"open",Toast.LENGTH_SHORT).show();
                // 查询 -- 扫描书的二维码 --
                ZXingLibrary.initDisplayOpinion(this);
                startActivityForResult(new Intent(ContextActivity.this, CaptureActivity.class), REQUEST_CODE);
                //
                break;
        }
        drawerLayout.closeDrawers();
        return super.onOptionsItemSelected(item);
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.book:
                switchFragment(from,fragmentOne);
                actionBar.setTitle("主页");
                break;
            case R.id.wifi:
                switchFragment(from,fragmentTwo);
                actionBar.setTitle("WIFI公社");
                break;
            case R.id.personInfo:
                switchFragment(from,fragmentThree);
                actionBar.setTitle("个人信息");
                break;
            case R.id.set:

                break;
            case R.id.about:
                break;
        }
        drawerLayout.closeDrawers();
        return true;
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
                        Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        Toast.makeText(ContextActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                break;
        }
    }
}
