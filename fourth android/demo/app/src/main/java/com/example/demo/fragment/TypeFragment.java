package com.example.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.demo.R;
import com.example.demo.RecipeDetailActivity;
import com.example.demo.adapter.RecipeAdapter;
import com.example.demo.data.RecipeBean;
import com.example.demo.data.TypeBean;
import com.example.demo.tools.CommonTool;
import com.example.demo.tools.Constants;
import com.example.demo.tools.CustomDialog;
import com.example.demo.tools.HttpTool;
import com.example.demo.tools.UrlConfig;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

public class TypeFragment extends Fragment {
    View view;
    private ArrayList<RecipeBean> recipeBeans = new ArrayList<>();
    private RecipeAdapter adapter;
    private TextView typeTV;
    private List<TypeBean> typeBeans = new ArrayList<>();
    private int typeId = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_type, container, false);
        initView();
        getData();
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //Called when the hidden state of the fragment has changed. Fragments start out not hidden;
        //this will be called whenever the fragment changes state from that.
        if(!hidden){getData();}
    }

    protected void initView() {
        //Get all the view.
        typeTV = view.findViewById(R.id.typeTV);
        GridView gv = view.findViewById(R.id.gv);
        adapter=new RecipeAdapter(getActivity(),recipeBeans);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Connect to the RecipeDetailActivity and start the RecipeDetailActivity
                Intent intent=new Intent(getActivity(), RecipeDetailActivity.class);
                intent.putExtra("id",recipeBeans.get(position).getId());
                intent.putExtra("uid",recipeBeans.get(position).getUid());
                startActivity(intent);
            }
        });
        typeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpParams params = new HttpParams();
                //Request show all recipe's type url.
                HttpTool.postList(UrlConfig.showAllType, params, new TypeToken<List<TypeBean>>() {
                }.getType(), new HttpTool.HttpListener() {
                    @Override
                    public void onComplected(Object... result) {
                        List<TypeBean> temp = (List<TypeBean>) result[0];
                        //If the recipe bean is null or the list size is 0, prompt the user there is no data
                        if (temp == null || temp.size() == 0) CommonTool.showToast("No data");
                        typeBeans.clear();
                        typeBeans.addAll(temp);
                        CustomDialog dialog = new CustomDialog(getActivity());
                        ArrayList<String> texts = new ArrayList<>();
                        for (int i = 0; i < typeBeans.size(); i++) {
                            //Add the name of the type bean to the list.
                            texts.add(typeBeans.get(i).getName());
                        }
                        dialog.bindListLayout(texts, new CustomDialog.DialogCommonFinishIF() {
                            @Override
                            public void onDialogFinished(Object... objects) {
                                int position = Integer.parseInt(objects[0].toString());
                                //Get type id.
                                typeId = typeBeans.get(position).getId();
                                //Set the text to the name of the type.
                                typeTV.setText(typeBeans.get(position).getName());
                                getData();
                            }
                        });
                        //Set the dialog.
                        CommonTool.setDialog(getActivity(), dialog, Gravity.CENTER, R.style.dialogWindowAnimButtomToTop, 1, 1);

                    }
                    @Override
                    public void onFailed(String msg) {
                        CommonTool.showToast(msg);
                    }
                });
            }
        });
    }

    protected void getData() {
        HttpParams params = new HttpParams();
        params.put("uid", Constants.userBean.getId());
        params.put("type",typeId);
        //Request the show recipes by type url.
        HttpTool.postList(UrlConfig.showByType, params, new TypeToken<List<RecipeBean>>() {
        }.getType(), new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                List<RecipeBean> temp = (List<RecipeBean>) result[0];
                //Remove all the elements in the recipe bean list
                recipeBeans.clear();
                //Append the all the elements to the recipe bean list
                recipeBeans.addAll(temp);
                //Notifies the attached observers that the underlying data has been changed
                //and any View reflecting the data set should refresh itself.
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailed(String msg) {
                CommonTool.showToast(msg);
            }
        });
    }
}
