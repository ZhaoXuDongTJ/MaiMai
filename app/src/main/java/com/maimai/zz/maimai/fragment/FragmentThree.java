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
    //

    private FloatingActionButton floatBtn;
    private Button contribute;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three,container,false);

        floatBtn = (FloatingActionButton)view.findViewById(R.id.floatBtn);
        floatBtn.setOnClickListener(this);


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
}
