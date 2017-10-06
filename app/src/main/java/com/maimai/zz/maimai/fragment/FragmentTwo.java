package com.maimai.zz.maimai.fragment;

import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maimai.zz.maimai.R;
import com.maimai.zz.maimai.SetActivity;
import com.maimai.zz.maimai.WIFisActivity;
import com.maimai.zz.maimai.bombs.BmobObjectID;
import com.maimai.zz.maimai.bombs.StudentInfo;
import com.maimai.zz.maimai.bombs.wifiInfo;
import com.maimai.zz.maimai.utils.WifiAd;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 87784 on 2017/10/2.
 */

public class FragmentTwo extends Fragment implements BaseFragment{

    private View view;
    private ImageView wifi;
    private Button buttonWifi,button2;
    private TextView memberOfWifi;
    private TextView ssidView;
    private TextView wifi_username,wifi_password;

    public FloatingActionButton floatBtns;
    private boolean temp;

    // 共享 存储
    public SharedPreferences.Editor editor;
    public SharedPreferences pref;
    private TextView textnumber,textenergy;
    private int num;
    private int energy;
    //wifi
    public String nowWifi;
    //
    public ArrayList<StudentInfo> wifilistQuery=null;
    public int is=0;
    public int ico;
    public boolean isWifiMenber = false;
    public Boolean hasWifiPassword = false;
    //

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_two,container,false);

        BmobQuery<wifiInfo> query = new BmobQuery<wifiInfo>();
        query.getObject(BmobObjectID.WIFI_INFO, new QueryListener<wifiInfo>() {

            @Override
            public void done(wifiInfo object, BmobException e) {
                if(e==null){
                    num=object.getMember();
                    energy=object.getEnergy();
                }else{
                    Log.i("bmob","shibai"+e.getMessage()+","+e.getErrorCode());
                }
            }

        });
        temp = true;

        initView();
        initListner();
        nowWifi = WifiAd.getWifiAdmin().GetSSID();
        ssidView.setText("您目前WIFI连接是 : "+nowWifi);
        return view;
    }

    @Override
    public void initView() {
        textnumber=(TextView)view.findViewById(R.id.textnumber);
        textenergy=(TextView)view.findViewById(R.id.textenergy);
        wifi_username=(TextView)view.findViewById(R.id.wifi_username);
        wifi_password=(TextView)view.findViewById(R.id.wifi_password);

        wifi = (ImageView)view.findViewById(R.id.wifiBlack);
        buttonWifi = (Button) view.findViewById(R.id.buttonWifi);
        button2 = (Button) view.findViewById(R.id.button2);
        floatBtns = (FloatingActionButton)view.findViewById(R.id.floatBtns);
        editor  = new ContextWrapper(getContext()).getSharedPreferences("data",MODE_PRIVATE).edit();
        pref = new ContextWrapper(getContext()).getSharedPreferences("data",MODE_PRIVATE);
        memberOfWifi = (TextView) view.findViewById(R.id.memberOfWifi);
        ssidView = (TextView) view.findViewById(R.id.tbwifi);


        BmobQuery<StudentInfo> stu = new BmobQuery<StudentInfo>();
        stu.getObject(pref.getString("ObjectID", ""), new QueryListener<StudentInfo>() {
            @Override
            public void done(StudentInfo studentInfo, BmobException e) {
                if(null == e){
                    if(studentInfo.getMember()){
                        isWifiMenber = true;
                        BmobQuery<StudentInfo> query = new BmobQuery<StudentInfo>();
                        query.addWhereEqualTo("isMember",true);
                        query.findObjects(new FindListener<StudentInfo>() {
                            @Override
                            public void done(List<StudentInfo> list, BmobException e) {
                                wifilistQuery  = (ArrayList<StudentInfo>) list;
                                ico = list.size();
                            }
                        });
                    }
                }
            }
        });





    }

    @Override
    public void initListner() {

        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (temp){
                    wifi.setImageResource(R.drawable.wifi_blue);
                    // 显示 密码
                    if(isWifiMenber){
                        wifi_username.setText("wifi:"+wifilistQuery.get(is).getStudentID());
                        wifi_password.setText("wifi:"+wifilistQuery.get(is).getWifiPassword());
                        is++;
                        if(is==ico){
                            is=0;
                        }
                    }else {
                        wifi.setImageResource(R.drawable.wifi_black);
                       Toast.makeText(getActivity(),"请加入公社，并在设置中设置密码",Toast.LENGTH_LONG).show();
                    }

                }else {
                    wifi.setImageResource(R.drawable.wifi_black);
                    // 去除 密码
                    wifi_username.setText("wifi:账号");
                    wifi_password.setText("wifi:密码");
                }
                temp = !temp;
            }
        });

        buttonWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WIFisActivity.class));
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<StudentInfo> stu = new BmobQuery<StudentInfo>();
                stu.getObject(pref.getString("ObjectID", ""), new QueryListener<StudentInfo>() {
                    @Override
                    public void done(StudentInfo studentInfo, BmobException e) {
                        if(null == e){
                            if(studentInfo.getMember()){
                                isWifiMenber = true;
                                memberOfWifi.setText("请利用wifi好好学习！");
                                Toast.makeText(getActivity(),"请利用wifi好好学习！",Toast.LENGTH_SHORT).show();
                                BmobQuery<StudentInfo> query = new BmobQuery<StudentInfo>();
                                query.addWhereEqualTo("isMember",true);
                                query.findObjects(new FindListener<StudentInfo>() {
                                    @Override
                                    public void done(List<StudentInfo> list, BmobException e) {
                                        wifilistQuery  = (ArrayList<StudentInfo>) list;
                                        ico = list.size();
                                    }
                                });
                            }else {
                                BmobQuery<StudentInfo> query = new BmobQuery<StudentInfo>();
                                query.getObject(pref.getString("ObjectID", ""), new QueryListener<StudentInfo>() {
                                    @Override
                                    public void done(StudentInfo studentInfo, BmobException e) {
                                        hasWifiPassword = studentInfo.getHasWiFIpassword();
                                        if(hasWifiPassword){
                                            StudentInfo studentInfo1 = new StudentInfo();
                                            studentInfo1.setMember(true);
                                            studentInfo1.update(pref.getString("ObjectID", ""), new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    Toast.makeText(getActivity(),"欢迎加入",Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            wifiInfo wifiInfo = new wifiInfo();
                                            wifiInfo.increment("member");
                                            wifiInfo.increment("energy",2);
                                            wifiInfo.update(BmobObjectID.WIFI_INFO, new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {

                                                }
                                            });

                                        }else {
                                            Toast.makeText(getActivity(),"请你先设置WIFI密码",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getActivity(), SetActivity.class));
                                        }
                                    }
                                });


                            }
                        }
                    }
                });
            }
        });

        floatBtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textnumber.setText("公社成员:"+num);
                textenergy.setText("公社能量:"+energy);
            }
        });

    }

    public void synthis(){

    }

    public void checkWifiName(String nowWifi){
        if(nowWifi.equals(WifiAd.SCHOOL_WIFI_SNAME)){
            Toast.makeText(getActivity(),"可以使用使用Wifi",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getActivity(),"请连接TJUT-WiFiS然后点击上面wifi图标",Toast.LENGTH_SHORT).show();
        }
    }



}
