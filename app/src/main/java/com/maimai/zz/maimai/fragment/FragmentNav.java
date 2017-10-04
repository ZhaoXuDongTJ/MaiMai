package com.maimai.zz.maimai.fragment;

import android.app.Fragment;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maimai.zz.maimai.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 92198 on 2017/10/4.
 */

public class FragmentNav extends Fragment {
    //
    private TextView name,info;
    //
    // 共享 存储
    public SharedPreferences.Editor editor;
    public SharedPreferences pref;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nav_head,container,false);

        editor  = new ContextWrapper(getContext()).getSharedPreferences("data",MODE_PRIVATE).edit();
        pref = new ContextWrapper(getContext()).getSharedPreferences("data",MODE_PRIVATE);

        name = (TextView) view.findViewById(R.id.name);
        name.setText(pref.getString("name",""));

        info = (TextView) view.findViewById(R.id.name);
        info.setText(pref.getString("id",""));

        return view;
    }
}
