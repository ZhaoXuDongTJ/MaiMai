package com.maimai.zz.maimai.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.maimai.zz.maimai.R;
import com.maimai.zz.maimai.WIFisActivity;
import com.maimai.zz.maimai.litepalTestActivity;

/**
 * Created by 87784 on 2017/10/2.
 */

public class FragmentOne extends Fragment implements BaseFragment,View.OnClickListener{

    private RollPagerView rollPagerView;
    private ImageButton imgBntOne,imgBntTwo,imgBntThree;
    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_one,container,false);

        initView();
        initListner();

        //设置轮播图片
        rollPagerView.setPlayDelay(4000);
        rollPagerView.setAnimationDurtion(500);
        rollPagerView.setAdapter(new MyRollPlayAdapter());
        rollPagerView.setHintView(new ColorPointHintView(getActivity(), Color.YELLOW,Color.WHITE));


        return view;
    }


    @Override
    public void initView() {
        rollPagerView = (RollPagerView)view.findViewById(R.id.rollPagerView);
        imgBntOne = (ImageButton) view.findViewById(R.id.imgBtnOne);
        imgBntTwo = (ImageButton) view.findViewById(R.id.imgBntTwo);
        imgBntThree = (ImageButton) view.findViewById(R.id.imgBntThree);
    }

    @Override
    public void initListner() {
        imgBntOne.setOnClickListener(this);
        imgBntTwo.setOnClickListener(this);
        imgBntThree.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBtnOne:
                Toast.makeText(getActivity(),"点击划一划体验购物新模式",Toast.LENGTH_SHORT).show();
                break;
            case R.id.imgBntTwo:
                startActivity(new Intent(getContext(),litepalTestActivity.class));                               // 点击 查看 专业书籍
                break;
            case R.id.imgBntThree:
                startActivity(new Intent(getContext(),WIFisActivity.class));
                break;
        }
    }

    private class MyRollPlayAdapter extends StaticPagerAdapter {
        private int[] imgs = {
                R.drawable.lunboshiyao5,
                R.drawable.lunboshiyao4,
        };


        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getCount() {
            return imgs.length;
        }
    }
}
