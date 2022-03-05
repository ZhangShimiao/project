package com.example.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.demo.AddRecipeActivity;
import com.example.demo.R;
import com.example.demo.SearchRecipesActivity;

import java.util.ArrayList;

public class MainFragment extends Fragment implements View.OnClickListener{
    View view;
    private TextView searchEt;
    private LinearLayout button1;
    private LinearLayout button2;
    private LinearLayout button3;
    private TextView[] texts=new TextView[3];
    private TextView[] underlines=new TextView[3];
    private TextView addRecipe;
    private FragmentManager fragmentManager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private int index=1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        initView();
        return view;
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button1) {
            index = 0;
            setCurrentFragment();
        } else if (v.getId() == R.id.button2) {
            index = 1;
            setCurrentFragment();
        } else if (v.getId() == R.id.button3) {
            index = 2;
            setCurrentFragment();
        }else if (v.getId() == R.id.addRecipe) {
            Intent intent=new Intent(getActivity(), AddRecipeActivity.class);
            startActivity(intent);
        }else if (v.getId() == R.id.searchEt) {
            Intent intent=new Intent(getActivity(), SearchRecipesActivity.class);
            startActivity(intent);
        }
    }

    protected void initView(){
        fragmentManager = getChildFragmentManager();
        addRecipe = view.findViewById(R.id.addRecipe);
        searchEt = view.findViewById(R.id.searchEt);
        button1 = view.findViewById(R.id.button1);
        texts[0] = view.findViewById(R.id.text1);
        underlines[0] = view.findViewById(R.id.line1);
        button2 = view.findViewById(R.id.button2);
        texts[1] = view.findViewById(R.id.text2);
        underlines[1] = view.findViewById(R.id.line2);
        button3 = view.findViewById(R.id.button3);
        texts[2] = view.findViewById(R.id.text3);
        underlines[2] = view.findViewById(R.id.line3);

        FollowFragment fragment1=new FollowFragment();
        mFragments.add(fragment1);
        RecommendFragment fragment2=new RecommendFragment();
        mFragments.add(fragment2);
        TypeFragment fragment3=new TypeFragment();
        mFragments.add(fragment3);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        addRecipe.setOnClickListener(this);
        searchEt.setOnClickListener(this);
        setCurrentFragment();
    }

    private void setCurrentFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (int i = 0; i < mFragments.size(); i++) {
            if (mFragments.get(i) != null) {
                transaction.hide(mFragments.get(i));
            }
            if (i == index) {
                texts[i].setTextColor(getResources().getColor(R.color.black));
                underlines[i].setVisibility(View.VISIBLE);
            } else {
                texts[i].setTextColor(getResources().getColor(R.color.text_color_999));
                underlines[i].setVisibility(View.INVISIBLE);
            }
        }
        if (!mFragments.get(index).isAdded()) {
            transaction.add(R.id.container, mFragments.get(index));
        }
        transaction.show(mFragments.get(index));
        transaction.commit();

    }
}
