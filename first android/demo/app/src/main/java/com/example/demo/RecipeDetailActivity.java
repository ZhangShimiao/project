package com.example.demo;


import android.content.Intent;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.demo.adapter.CommentAdapter;
import com.example.demo.data.BaseBean;
import com.example.demo.data.CommentBean;
import com.example.demo.data.RecipeBean;
import com.example.demo.data.RecipeResponseBean;
import com.example.demo.tools.CommonTool;
import com.example.demo.tools.Constants;
import com.example.demo.tools.GlideLoadUtils;
import com.example.demo.tools.HttpTool;
import com.example.demo.tools.UrlConfig;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.HttpParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends BaseActivity implements View.OnClickListener {
    //The text view of recipe title
    private TextView recipeTitle;
    //The image view of recipe's picture and user's head image
    private ImageView iv, headImg;
    //The text view of user's name, recipe's create time, recipe's materials, recipe's steps,
    //recipe's content
    private TextView nameTv;
    private TextView timeTv;
    private TextView materialsTv;
    private TextView stepsTv;
    private TextView contentTv;
    //The button which can publish comment
    private TextView btn1;
    //The button which can collect the recipe or cancel collect
    private TextView btn2;
    //The edit text of comment
    private EditText commentEt;
    private int beCommentUserId;

    private int id, uid;
    private RecipeBean recipeBean;
    //A list stores comment entity
    private ArrayList<CommentBean> commentBeans = new ArrayList<>();
    private CommentAdapter adapter;

    @Override
    public int initLayout() {
        return R.layout.activity_recipe_detail;
    }

    @Override
    protected void initData() {
        id = getIntent().getIntExtra("id", -1);
        uid = getIntent().getIntExtra("uid", -1);
        HttpParams params = new HttpParams();
        params.put("id", id);
        params.put("uid", Constants.userBean.getId());
        //Request recipe detail url
        HttpTool.postObject(UrlConfig.recipesDetail, params, RecipeResponseBean.class, new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                RecipeResponseBean temp = (RecipeResponseBean) result[0];
                recipeBean = temp.data;
                bindData();
            }
            @Override
            public void onFailed(String msg) {
                CommonTool.showToast(msg);
            }
        });
        //Get the recipe's comment data
        getCommentData();
    }
    protected void bindData() {
        //Calling scan() to add scan history
        scan();
        GlideLoadUtils imageLoader = new GlideLoadUtils(this);
        //Load the image according to the recipe's picture's path.
        if (recipeBean.getPic().contains("v.ylapi.cn")) {
            imageLoader.loadImage(recipeBean.getPic(), iv, true);
        } else {
            imageLoader.loadImage(UrlConfig.BaseURL + recipeBean.getPic(), iv, true);
        }
        //Set the recipe's create time format.
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String time = simpleDateFormat.format(recipeBean.getCreatetime());
        timeTv.setText("Published on：" + time);
        //Set the recipe's name, materials, steps, content and title.
        nameTv.setText(recipeBean.getUname());
        materialsTv.setText(recipeBean.getMaterials());
        stepsTv.setText(recipeBean.getSteps());
        contentTv.setText(recipeBean.getContent());
        recipeTitle.setText(recipeBean.getTitle());
        //If getIsCollection() result equals 0, set the button text to "Collect",
        //else, set it to "Cancel Collect".
        if (recipeBean.getIsCollection() == 0) {
            btn2.setText("Collect");
        } else {
            btn2.setText("Cancel Collect");
        }
        //Load the user's head image.
        imageLoader.loadCircularImage(UrlConfig.BaseURL + recipeBean.getHeadimg(), R.mipmap.default_head, R.mipmap.default_head, headImg);
    }
    public void scan() {
        //Incoming parameters: rid, uid.
        HttpParams params = new HttpParams();
        params.put("rid", recipeBean.getId());
        params.put("uid", Constants.userBean.getId());
        //Request the add scan history url
        HttpTool.postObject(UrlConfig.addScan, params, BaseBean.class, new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
            }
            @Override
            public void onFailed(String msg) {
            }
        });
    }

    protected void getCommentData() {
        //Incoming parameters: rid.
        HttpParams params = new HttpParams();
        params.put("rid", id);
        //Request the get comment list url
        HttpTool.postList(UrlConfig.getCommentList, params, new TypeToken<List<CommentBean>>() {
        }.getType(), new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                List<CommentBean> temp = (List<CommentBean>) result[0];
                //Remove all the elements in the comment bean list.
                commentBeans.clear();
                //Append the all the elements to the recipe bean list.
                commentBeans.addAll(temp);
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

    @Override
    protected void initView() {
        //Find the title in view_common_title.xml
        TextView title = findViewById(R.id.title);
        //Set the title text
        title.setText("Recipe Details");
        //Initialise all the view
        View header = LayoutInflater.from(this).inflate(R.layout.header_recipe_detail, null);
        recipeTitle = header.findViewById(R.id.recipeTitle);
        iv = header.findViewById(R.id.iv);
        headImg = header.findViewById(R.id.headImg);
        nameTv = header.findViewById(R.id.nameTv);
        timeTv = header.findViewById(R.id.timeTv);
        materialsTv = header.findViewById(R.id.materialsTv);
        stepsTv = header.findViewById(R.id.stepsTv);
        contentTv = header.findViewById(R.id.contentTv);
        commentEt = findViewById(R.id.commentEt);
        ListView listView = findViewById(R.id.listView);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        adapter = new CommentAdapter(this, commentBeans, uid);
        listView.setAdapter(adapter);
        listView.addHeaderView(header);
        beCommentUserId = uid;
        //Set click listener
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        headImg.setOnClickListener(this);
        nameTv.setOnClickListener(this);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beCommentUserId = uid;
                //Set the prompt for the comment edit text
                commentEt.setHint("Please enter comment..");
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                beCommentUserId = commentBeans.get(position - 1).getCommentuserid();
                commentEt.setHint("@" + commentBeans.get(position - 1).getCommentUserName());
            }
        });
    }

    @Override
    public void onClick(View v) {
        //Get the ID of the currently clicked view,if it is btn1
        if (v.getId() == R.id.btn1) {
            //Hide the keyboard and calling comment() to add comment
            hideKeyboard();
            comment();
        }
        //Get the ID of the currently clicked view,if it is btn2
        else if (v.getId() == R.id.btn2) {
            //calling collection() to add collection
            collection();
        }
        //Get the ID of the currently clicked view,if it is nameTv or headImg
        else if (v.getId() == R.id.nameTv || v.getId() == R.id.headImg) {
            Intent intent;
            //If the recipe's publisher is not the current user
            if (!recipeBean.getUid().equals(Constants.userBean.getId())) {
                //Connect to the UserMsgActivity and start UserMsgActivity
                intent = new Intent(this, UserMsgActivity.class);
                intent.putExtra("id", recipeBean.getUid());
            }else{
                //If the recipe's publisher is the current user,
                //connect to the MainActivity and start MainActivity
                //and set the fragment is SelfFragment.
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("index", 2);
            }
            startActivity(intent);
        }
    }
    public void comment() {
        //Convert the input to string
        String content = commentEt.getText().toString();
        //If the comment entered is empty, prompt the user to enter comment.
        if (TextUtils.isEmpty(content)) {
            CommonTool.showToast("Comment content cannot be empty!");
            return;
        }
        //If the comment content is empty, the button can not be clicked.
        btn1.setClickable(false);
        //Incoming parameters: rid, commentuserid, becommentuserid, content.
        HttpParams params = new HttpParams();
        params.put("rid", recipeBean.getId());
        params.put("commentuserid", Constants.userBean.getId());
        params.put("becommentuserid", beCommentUserId);
        params.put("content", content);
        //Request the add comment url
        HttpTool.postObject(UrlConfig.addComment, params, BaseBean.class, new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                //Set the button can be clicked.
                btn1.setClickable(true);
                BaseBean bean = (BaseBean) result[0];
                if (bean.code == 0) {
                    //Get the comment data, and prompt comment successful.
                    getCommentData();
                    CommonTool.showToast("Comment successful!");
                    //Clear the input of the comment edit text
                    commentEt.setText("");
                } else {
                    CommonTool.showToast(bean.msg);
                }
            }

            @Override
            public void onFailed(String msg) {
                btn1.setClickable(true);
                CommonTool.showToast(msg);
            }
        });
    }
    public void collection() {
        btn2.setClickable(false);
        HttpParams params = new HttpParams();
        params.put("rid", recipeBean.getId());
        params.put("uid", Constants.userBean.getId());
        //Request the collection url.
        HttpTool.postObject(UrlConfig.collection, params, BaseBean.class, new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                btn2.setClickable(true);
                BaseBean bean = (BaseBean) result[0];
                if (bean.code == 0) {
                    //If get the data successfully, calling initData()
                    initData();
                } else {
                    CommonTool.showToast(bean.msg);
                }
            }
            @Override
            public void onFailed(String msg) {
                btn2.setClickable(true);
                CommonTool.showToast(msg);
            }
        });
    }
}