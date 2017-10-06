package com.maimai.zz.maimai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.maimai.zz.maimai.R;
import com.maimai.zz.maimai.WIFisActivity;

/**
 * Created by 87784 on 2017/10/2.
 */

public class FragmentTwo extends Fragment implements BaseFragment{

    private View view;
    private ImageView wifi;
    private Button buttonWifi,button2;

    public FloatingActionButton floatBtns;
    private boolean temp;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

            }
        });

    }


    public void synthis(){
        


    }



}
