package com.example.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private TextView[] texts=new TextView[3];
    private TextView[] underlines=new TextView[3];
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
        //Get the ID of the currently clicked view,if it is button1
        if (v.getId() == R.id.button1) {
            //The follow page.
            index = 0;
            setCurrentFragment();
        } else if (v.getId() == R.id.button2) {
            //The recipe page
            index = 1;
            setCurrentFragment();
        } else if (v.getId() == R.id.button3) {
            //The recipe's type page
            index = 2;
            setCurrentFragment();
        }else if (v.getId() == R.id.addRecipe) {
            //Get the ID of the currently clicked view,if it is addRecipe
            //Connect to AddRecipeActivity and start it.
            Intent intent=new Intent(getActivity(), AddRecipeActivity.class);
            startActivity(intent);
        }else if (v.getId() == R.id.searchEt) {
            //Get the ID of the currently clicked view,if it is searchEt
            //Connect to SearchRecipesActivity and start it.
            Intent intent=new Intent(getActivity(), SearchRecipesActivity.class);
            startActivity(intent);
        }
    }

    protected void initView(){
        //Return a private FragmentManager for placing and managing Fragments
        //inside of this Fragment.
        fragmentManager = getChildFragmentManager();
        //Get all the view.
        TextView addRecipe = view.findViewById(R.id.addRecipe);
        TextView searchEt = view.findViewById(R.id.searchEt);
        //Get the button, text, and underline of follow.
        LinearLayout button1 = view.findViewById(R.id.button1);
        texts[0] = view.findViewById(R.id.text1);
        underlines[0] = view.findViewById(R.id.line1);
        //Get the button, text, and underline of recipe.
        LinearLayout button2 = view.findViewById(R.id.button2);
        texts[1] = view.findViewById(R.id.text2);
        underlines[1] = view.findViewById(R.id.line2);
        //Get the button, text, and underline of recipe's type.
        LinearLayout button3 = view.findViewById(R.id.button3);
        texts[2] = view.findViewById(R.id.text3);
        underlines[2] = view.findViewById(R.id.line3);
        //Add the follow fragment to the fragment list.
        FollowFragment fragment1=new FollowFragment();
        mFragments.add(fragment1);
        //Add the recommend fragment to the fragment list.
        RecommendFragment fragment2=new RecommendFragment();
        mFragments.add(fragment2);
        //Add the type fragment to the fragment list.
        TypeFragment fragment3=new TypeFragment();
        mFragments.add(fragment3);
        //Set click listener
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        addRecipe.setOnClickListener(this);
        searchEt.setOnClickListener(this);
        //Set the current fragment.
        setCurrentFragment();
    }

    private void setCurrentFragment() {
        //Start a series of edit operations on the Fragments associated with this FragmentManager.
        //对与此FragmentManager关联的片段启动一系列编辑操作。
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (int i = 0; i < mFragments.size(); i++) {
            //If the fragment is not null, then hide the fragment
            if (mFragments.get(i) != null) {
                transaction.hide(mFragments.get(i));
            }
            //Get which button is currently clicked and execute the fragment corresponding to the button
            if (i == index) {
                //Set the text color to black, and the underline is visible.
                texts[i].setTextColor(getResources().getColor(R.color.black));
                underlines[i].setVisibility(View.VISIBLE);
            } else {
                //Set the text color to gray, and the underline is invisible.
                texts[i].setTextColor(getResources().getColor(R.color.text_color_999));
                underlines[i].setVisibility(View.INVISIBLE);
            }
        }
        //If the index fragment is not added to it, call to add it
        if (!mFragments.get(index).isAdded()) {
            transaction.add(R.id.container, mFragments.get(index));
        }
        //Show the fragment.
        transaction.show(mFragments.get(index));
        //Enter fragment cycle.
        transaction.commit();

    }
}
