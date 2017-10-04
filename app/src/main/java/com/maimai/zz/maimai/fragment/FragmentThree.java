package com.maimai.zz.maimai.fragment;

import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.maimai.zz.maimai.MainActivity;
import com.maimai.zz.maimai.R;
import com.maimai.zz.maimai.bombs.StudentInfo;
import com.maimai.zz.maimai.utils.AppConfig;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 87784 on 2017/10/2.
 */

public class FragmentThree extends Fragment implements View.OnClickListener{

    // 共享 存储
    public SharedPreferences.Editor editor;
    public SharedPreferences pref;
    //
    public TextView frag3Name;
    public Button jieyue;
    public Button gongxiang;
    public Button send;
    public Button receiver;
    //

    private FloatingActionButton floatBtn;
    private Button contribute;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three,container,false);

        floatBtn = (FloatingActionButton)view.findViewById(R.id.floatBtn);
        floatBtn.setOnClickListener(this);
////
        jieyue = (Button) view.findViewById(R.id.jieyue);
        gongxiang = (Button) view.findViewById(R.id.gongxiang);
        send = (Button) view.findViewById(R.id.send);
        receiver = (Button) view.findViewById(R.id.receiver);


        editor  = new ContextWrapper(getContext()).getSharedPreferences("data",MODE_PRIVATE).edit();
        pref = new ContextWrapper(getContext()).getSharedPreferences("data",MODE_PRIVATE);

        frag3Name = (TextView) view.findViewById(R.id.frag3Name);
        frag3Name.setText(pref.getString("name",""));

        //跳转 页面的 增加贡献模板
        contribute = (Button) view.findViewById(R.id.contribute);
        contribute.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                synchroModuleNumber();
            }
        });






        return view;
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getActivity(),"float",Toast.LENGTH_SHORT).show();

        editor.clear();
        editor.apply();
        Intent i = new ContextWrapper(getContext()).getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(new ContextWrapper(getContext()).getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void synchroModuleNumber(){
        //  同步 数据;
        BmobQuery<StudentInfo> stu = new BmobQuery<StudentInfo>();
        stu.getObject(pref.getString("ObjectID", ""), new QueryListener<StudentInfo>() {
            @Override
            public void done(StudentInfo studentInfo, BmobException e) {
                if(e==null){
                    contribute.setText(AppConfig.String_ModuleNumber + studentInfo.getMouldContribute());
                    jieyue.setText(AppConfig.String_jieyue + studentInfo.getBookBuy());
                    gongxiang.setText(AppConfig.String_gongxiang + studentInfo.getBookSell());
                    send.setText(AppConfig.String_fahuo + studentInfo.getDeliverGood());
                    receiver.setText(AppConfig.String_shouhuo + studentInfo.getReceiptGood());
                }
            }
        });
    }



}
