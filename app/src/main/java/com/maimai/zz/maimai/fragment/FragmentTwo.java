package com.maimai.zz.maimai.fragment;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.maimai.zz.maimai.R;
import com.maimai.zz.maimai.WIFisActivity;
import com.maimai.zz.maimai.bombs.BmobObjectID;
import com.maimai.zz.maimai.bombs.StudentInfo;
import com.maimai.zz.maimai.bombs.wifiInfo;
import com.maimai.zz.maimai.utils.WifiAd;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
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

    public FloatingActionButton floatBtns;
    private boolean temp;

    // 共享 存储
    public SharedPreferences.Editor editor;
    public SharedPreferences pref;

    //wifi
//    private WifiManager mWifiManager = null;
//    private WifiInfo mWifiInfo = null;
//    Context mContext;
//    public Context getmContext() {
//        return mContext;
//    }
//    public void setmContext(Context mContext) {
//        this.mContext = mContext;
//    }
//    public void getWifiMeathod() {
//        mWifiManager = (WifiManager) mContext.getSystemService(mContext.WIFI_SERVICE);
//
//        mWifiInfo = mWifiManager.getConnectionInfo();
//    }
//    public String GetSSID() {
//        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getSSID();
//    }
//
//


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_two,container,false);

        temp = true;

        initView();
        initListner();
        ssidView.setText(WifiAd.getWifiAdmin().GetSSID());
        return view;
    }

    @Override
    public void initView() {

        wifi = (ImageView)view.findViewById(R.id.wifiBlack);
        buttonWifi = (Button) view.findViewById(R.id.buttonWifi);
        button2 = (Button) view.findViewById(R.id.button2);
        floatBtns = (FloatingActionButton)view.findViewById(R.id.floatBtns);
        editor  = new ContextWrapper(getContext()).getSharedPreferences("data",MODE_PRIVATE).edit();
        pref = new ContextWrapper(getContext()).getSharedPreferences("data",MODE_PRIVATE);
        memberOfWifi = (TextView) view.findViewById(R.id.memberOfWifi);
        ssidView = (TextView) view.findViewById(R.id.tbwifi);
    }

    @Override
    public void initListner() {

        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (temp){
                    wifi.setImageResource(R.drawable.wifi_blue);

                }else {
                    wifi.setImageResource(R.drawable.wifi_black);
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
                                memberOfWifi.setText("请利用wifi好好学习！");
                            }else {
                                StudentInfo studentInfo1 = new StudentInfo();
                                studentInfo1.setMember(true);
                                studentInfo1.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

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
                            }
                        }
                    }
                });
            }
        });

        floatBtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void synthis(){



    }


}
