package com.example.demo;

import androidx.annotation.Nullable;

import android.Manifest;
import android.content.Intent;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo.data.BaseBean;
import com.example.demo.data.UploadBean;
import com.example.demo.data.UserResonseBean;
import com.example.demo.tools.CommonTool;
import com.example.demo.tools.Constants;
import com.example.demo.tools.GlideLoadUtils;
import com.example.demo.tools.HttpTool;
import com.example.demo.tools.UrlConfig;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.model.HttpParams;

import java.util.List;

public class ChangeSelfActivity extends BaseActivity implements View.OnClickListener {

    //The image view of head image, name edit text, save button.
    private ImageView headImg;
    private EditText nameEt;
    private TextView butOk;

    private String name,headImgPath,headImgUrl;


    @Override
    public int initLayout() {
        return R.layout.activity_change_self;
    }

    @Override
    protected void initData() {
        HttpParams params = new HttpParams();
        params.put("id", Constants.userBean.getId());
        HttpTool.postObject(UrlConfig.GetUser, params, UserResonseBean.class, new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                UserResonseBean bean = (UserResonseBean) result[0];
                if (bean.code == 0) {
                    Constants.userBean = bean.data;
                    CommonTool.spPutString("userbean", new Gson().toJson(Constants.userBean));
                    bindData();
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
    private void bindData() {
        GlideLoadUtils imageLoader = new GlideLoadUtils(this);
        imageLoader.loadCircularImage(UrlConfig.BaseURL + Constants.userBean.getHeadimg(), R.mipmap.default_head, R.mipmap.default_head, headImg);
        headImgUrl = Constants.userBean.getHeadimg();
        nameEt.setText(Constants.userBean.getNickname());
    }
    @Override
    protected void initView() {
        TextView titleTv = findViewById(R.id.title);
        titleTv.setText("Personal information");
        headImg = findViewById(R.id.headImg);
        nameEt = findViewById(R.id.edit_name);
        butOk = findViewById(R.id.but_ok);
        butOk.setOnClickListener(this);
        headImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.but_ok) {
            save();
        } else if (v.getId() == R.id.headImg) {
            verifyStoragePermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionResultIF() {
                @Override
                public void permissionResult(boolean suc) {
                    if (suc) {
                        pickFile(1);
                    } else {
                        CommonTool.showLog("No permission");
                    }
                }
            });
        }
    }
    public void save() {
        name = nameEt.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            CommonTool.showToast("Please enter nickname");
            return;
        }
        if (TextUtils.isEmpty(headImgPath)) {
            commit();
        } else {
            if (!TextUtils.isEmpty(headImgPath)) {
                upload();
            }
        }
    }
    private void upload() {
        showLoadingDialog();
        HttpTool.postFile(headImgPath, UploadBean.class, new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                UploadBean bean = (UploadBean) result[0];
                headImgUrl = bean.data;
                CommonTool.showLog("Get remote path=" + bean.data);
                commit();
                hideLoadingDialog();
            }
            @Override
            public void onFailed(String msg) {
                butOk.setClickable(true);
                hideLoadingDialog();
                CommonTool.showToast(msg);
            }
        });
    }

    private void commit() {
        HttpParams params = new HttpParams();
        params.put("id", Constants.userBean.getId());
        params.put("account", Constants.userBean.getAccount());
        params.put("question", Constants.userBean.getQuestion());
        params.put("answer", Constants.userBean.getAnswer());
        params.put("nickname", name);
        params.put("headimg", headImgUrl);

        HttpTool.postObject(UrlConfig.UpdateSelf, params, BaseBean.class, new HttpTool.HttpListener() {
            @Override
            public void onComplected(Object... result) {
                BaseBean bean = (BaseBean) result[0];
                if (bean.code == 0) {
                    CommonTool.showToast("Save successful！");
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
    private void pickFile(int requstCode) {
//        线程，可以看作是进程的一个实体，是CPU调度和分派的基本单位，它是比进程更小的能独立运行的基本单位
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //Enter album
                //Start pictureSelector for AddRecipeActivity
                PictureSelector.create(ChangeSelfActivity.this)
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
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data == null) return;
            // 图片、视频、音频选择结果回调
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            if (selectList.size() > 0) {
                LocalMedia media = selectList.get(0);
                headImgPath = media.getPath();
                headImgUrl = null;
                CommonTool.showLog("path=" + headImgPath);
                GlideLoadUtils imageLoader = new GlideLoadUtils(this);
                imageLoader.loadCircularImage(headImgPath, R.mipmap.default_head,R.mipmap.default_head,headImg);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}