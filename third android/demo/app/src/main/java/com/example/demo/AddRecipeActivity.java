package com.example.demo;

import androidx.annotation.Nullable;

import android.Manifest;
import android.content.Intent;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo.data.BaseBean;
import com.example.demo.data.TypeBean;
import com.example.demo.data.UploadBean;
import com.example.demo.tools.CommonTool;
import com.example.demo.tools.Constants;
import com.example.demo.tools.CustomDialog;
import com.example.demo.tools.GlideLoadUtils;
import com.example.demo.tools.HttpTool;
import com.example.demo.tools.UrlConfig;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

public class AddRecipeActivity extends BaseActivity implements View.OnClickListener {
    //The title of the login page
    private TextView titleTv;
    //The back button
    private ImageView backIv;
    //The line
    private TextView line;
    //The edit text of recipe's title
    private EditText titleEt;
    //The text view of recipe's type
    private TextView typeTv;
    //The edit text of recipe's materials
    private EditText materialsEt;
    //The edit text of recipe's steps
    private EditText stepsEt;
    //The edit text of recipe's description
    private EditText descriptionEt;
    //The image view which contains the recipe's picture
    private ImageView picIv;
    //Publish recipe button
    private TextView commitBtn;
    //The string variables of pic, picUrl
    private String pic, picUrl;
    //The string variables of title, materials, steps, content
    private String title, materials, steps, content;
    //The list which contains recipe's type bean
    private List<TypeBean> typeBeans = new ArrayList<>();
    private int typeid = -1;

    @Override
    public int initLayout() {
        return R.layout.activity_add_recipe;
    }

    @Override
    protected void initData() {}

    @Override
    protected void initView() {
        //Find the title in view_common_title
        titleTv = findViewById(R.id.title);
        //Find the backIv in view_common_title
        backIv = findViewById(R.id.backIv);
        //Find the line in view_common_title
        line = findViewById(R.id.line);
        //The view of recipe's title
        titleEt = findViewById(R.id.titleEt);
        //The view of recipe's type
        typeTv = findViewById(R.id.typeTv);
        //The view of recipe's materials
        materialsEt = findViewById(R.id.materialsEt);
        //The view of recipe's steps
        stepsEt = findViewById(R.id.stepsEt);
        //The view of recipe's description
        descriptionEt = findViewById(R.id.contentEt);
        //The view of recipe's picture
        picIv = findViewById(R.id.picIv);
        //The view of publish recipe button
        commitBtn = findViewById(R.id.commitBtn);
        //Set the title text to "Publish Recipe"
        titleTv.setText("Publish Recipe");
        //Button monitoring
        commitBtn.setOnClickListener(this);
        picIv.setOnClickListener(this);
        typeTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Get the ID of the currently clicked view,if it is commitBtn
        if (v.getId() == R.id.commitBtn) {
            //calling commit() to publish the recipe
            commit();
        }
        //Get the ID of the currently clicked view,if it is typeTv
        else if (v.getId() == R.id.typeTv) {
            //calling showTypeDialog() to show the recipe's type
            showTypeDialog();
        }
        //Get the ID of the currently clicked view,if it is picIv
        else if (v.getId() == R.id.picIv) {
            //calling the verifyStoragePermission() to verify the storage permission
            verifyStoragePermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionResultIF() {
                @Override
                public void permissionResult(boolean suc) {
                    if (suc) {
                        //calling the pickFile() to select picture
                        pickFile(1);
                    } else {
                        CommonTool.showLog("No permission to use");
                    }
                }
            });
        }
    }

    public void commit() {
        //Convert the inputs to string or text, and trim() is the method
        //of removing spaces on both sides.
        title = titleEt.getText().toString().trim();
        materials = materialsEt.getText().toString().trim();
        steps = stepsEt.getText().toString().trim();
        content = descriptionEt.getText().toString().trim();
        //If the recipe's title entered is empty, the user will be prompted to enter the title
        if (TextUtils.isEmpty(title)) {
            CommonTool.showToast("Please enter title...");
            return;
        }
        //If the variable typeid is -1, the user will be prompted to select a recipe's type
        if (typeid == -1) {
            CommonTool.showToast("Please select type");
            return;
        }
        //If the recipe's materials entered is empty, the user will be prompted to enter the materials
        if (TextUtils.isEmpty(materials)) {
            CommonTool.showToast("Please enter materials...");
            return;
        }
        //If the recipe's steps entered is empty, the user will be prompted to enter the steps
        if (TextUtils.isEmpty(steps)) {
            CommonTool.showToast("Please enter steps...");
            return;
        }
        //If the recipe's content entered is empty, the user will be prompted to enter the description
        if (TextUtils.isEmpty(content)) {
            CommonTool.showToast("Please enter description...");
            return;
        }
        //If the recipe's picture entered is empty, the user will be prompted to select a picture
        if (TextUtils.isEmpty(pic)) {
            CommonTool.showToast("Please select a picture");
            return;
        }
        //Upload all data to publish the recipe
        upload();
    }
    private void upload() {
        //Show Loading dialog
        showLoadingDialog();
        HttpTool.postFile(pic, UploadBean.class, new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                UploadBean bean = (UploadBean) result[0];
                picUrl = bean.data;
                CommonTool.showLog("获取到到远程路径=" + bean.data);
                //If the picture url is not empty
                if (!TextUtils.isEmpty(picUrl)) {
                    //calling save() to save all data in the database
                    save();
                    //Hide the loading dialog
                    hideLoadingDialog();
                }
            }

            @Override
            public void onFailed(String msg) {
                commitBtn.setClickable(true);
                hideLoadingDialog();
                CommonTool.showToast(msg);
            }
        });

    }

    private void save() {
        //Incoming parameters: title, content, materials, steps, picUrl, typeid, uid
        HttpParams params = new HttpParams();
        params.put("title", title);
        params.put("content", content);
        params.put("materials", materials);
        params.put("steps", steps);
        params.put("pic", picUrl);
        params.put("typeid", typeid);
        params.put("uid", Constants.userBean.getId());
        //Request add recipe url
        HttpTool.postObject(UrlConfig.addRecipes, params, BaseBean.class, new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                BaseBean bean = (BaseBean) result[0];
                if (bean.code == 0) {
                    //If get the data successfully, prompt the user publish successfully
                    CommonTool.showToast("Publish successfully！");
                    //Finish the add recipe activity
                    finish();
                } else {
                    CommonTool.showToast(bean.msg);
                }
            }
            @Override
            public void onFailed(String msg) {
                CommonTool.showToast(msg);
            }
        });
    }
    private void showTypeDialog() {
        HttpParams params = new HttpParams();
        //Request get all recipe's type url
        HttpTool.postList(UrlConfig.getAllType, params, new TypeToken<List<TypeBean>>() {
        }.getType(), new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                List<TypeBean> temp = (List<TypeBean>) result[0];
                //If the type of recipe is null or the list size is 0, prompt the user there is no data
                if (temp == null || temp.size() == 0) CommonTool.showToast("No data");
                //Remove all the elements in the type bean list
                typeBeans.clear();
                //Append the all the elements to the type bean list
                typeBeans.addAll(temp);
                //Create a dialog object
                CustomDialog dialog = new CustomDialog(AddRecipeActivity.this);
                //A list stores the text of the recipe's type
                ArrayList<String> texts = new ArrayList<>();
                for (int i = 0; i < typeBeans.size(); i++) {
                    //Add the type name to the texts
                    texts.add(typeBeans.get(i).getName());
                }
                dialog.bindListLayout(texts, new CustomDialog.DialogCommonFinishIF() {
                    @Override
                    public void onDialogFinished(Object... objects) {
                        //Display the type names in the order of
                        //the type of recipes
                        int position = Integer.parseInt(objects[0].toString());
                        typeid = typeBeans.get(position).getId();
                        typeTv.setText(typeBeans.get(position).getName());
                    }
                });
                //Set the dialog of choosing the recipe's type
                CommonTool.setDialog(AddRecipeActivity.this, dialog, Gravity.CENTER, R.style.dialogWindowAnimButtomToTop, 1, 1);
            }
            @Override
            public void onFailed(String msg) {
                CommonTool.showToast(msg);
            }
        });
    }
    private void pickFile(int requstCode) {
//        线程，可以看作是进程的一个实体，是CPU调度和分派的基本单位，它是比进程更小的能独立运行的基本单位
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //Enter album
                //Start pictureSelector for AddRecipeActivity
                PictureSelector.create(AddRecipeActivity.this)
                        .openGallery(PictureMimeType.ofImage())//Select the image
                        .theme(R.style.picture_white_style)//Set the theme style 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                        .imageSpanCount(4)//Number of displays per line 每行显示个数
                        //.isAndroidQTransform(false)// 是否需要处理Android Q 拷贝至应用沙盒的操作，只针对compress(false); && .isEnableCrop(false);有效,默认处理
                        .selectionMode(PictureConfig.SINGLE)//Set the selection mode to single choice
                        .isCamera(true)//Show camera button 是否显示拍照按钮
                        .isZoomAnim(true)//Picture list click zoom effect 图片列表点击 缩放效果 默认true
                        .synOrAsy(true)//Synchronous compression 同步true或异步false 压缩 默认同步
                        .isGif(false)//Don't show GIF pictures 是否显示gif图片
                        .freeStyleCropEnabled(true)//The clipping box can be dragged 裁剪框是否可拖拽
                        .circleDimmedLayer(true)//Circular clipping 是否圆形裁剪
                        .minimumCompressSize(100)//Pictures smaller than 100kb are not compressed
                        .forResult(requstCode);//Start to select media and wait for result
            }
        });
        thread.start();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //dispatch incoming result to the correct fragment
        //calling pickFile() successfully
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data == null) return;
            //Picture selection result callback
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            //If the picture selection result size is larger than 0
            if (selectList.size() > 0) {
                //Get the media
                LocalMedia media = selectList.get(0);
                //Get the path of the picture
                pic = media.getPath();
                CommonTool.showLog("path=" + pic);
                GlideLoadUtils imageLoader = new GlideLoadUtils(this);
                //Load picture
                imageLoader.loadImage(pic, picIv, true);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}