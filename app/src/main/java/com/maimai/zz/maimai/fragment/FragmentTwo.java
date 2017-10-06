package com.maimai.zz.maimai.fragment;

import android.content.Intent;
import android.net.wifi.WifiInfo;
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
import com.maimai.zz.maimai.WIFisActivity;
import com.maimai.zz.maimai.bombs.BmobObjectID;
import com.maimai.zz.maimai.bombs.wifiInfo;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 87784 on 2017/10/2.
 */

public class FragmentTwo extends Fragment implements BaseFragment{
    private int gnumber;
    private int generge;
    private View view;
    private TextView numbert,energet;
    private ImageView wifi;
    private Button buttonWifi,button2;
    public FloatingActionButton floatBtns;
    private boolean temp;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        BmobQuery<wifiInfo> query = new BmobQuery<wifiInfo>();
        query.getObject(BmobObjectID.WIFI_INFO, new QueryListener<wifiInfo>() {

            @Override
            public void done(wifiInfo object, BmobException e) {
                if(e==null){
                    gnumber = object.getMember();
                    generge = object.getEnergy();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }

        });
        view = inflater.inflate(R.layout.fragment_two,container,false);
        temp = true;

        initView();
        initListner();

        return view;

    }

    @Override
    public void initView() {
        wifi = (ImageView)view.findViewById(R.id.wifiBlack);
        buttonWifi = (Button) view.findViewById(R.id.buttonWifi);
        button2 = (Button) view.findViewById(R.id.button2);
        floatBtns = (FloatingActionButton)view.findViewById(R.id.floatBtns);
        numbert =(TextView)view.findViewById(R.id.textnumber);
        energet = (TextView)view.findViewById(R.id.textenergy);
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

            }
        });

        floatBtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numbert.setText("公社成员:"+gnumber);
                energet.setText("公社能量:"+generge);
            }
        });

    }


    public void synthis(){
        


    }



}
