package com.maimai.zz.maimai.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.maimai.zz.maimai.R;

import rx.Subscription;

/**
 * Created by 87784 on 2017/10/2.
 */

public class FragmentTwo extends Fragment implements BaseFragment{

    private View view;
    private ImageView wifi;
    private ListView access_points_f2;

    private boolean temp;

    private Subscription wifiSubscription;

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
        access_points_f2 = (ListView) view.findViewById(R.id.access_points);
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
    }





}
