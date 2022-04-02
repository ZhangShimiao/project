package com.example.demo.tools;

public class UrlConfig {
    //The url path
    public static final boolean LOG_FLAG=true;

    //Base url
    public static final String BaseURL="http://192.168.1.100:8888";
    public static final String URL=BaseURL;
    //Login url
    public static final String LOGIN_URL="/user/userLogin";
    //Register url
    public static final String REGIST_URL ="/user/addUser";
    //The url which can get the user's information through the account.
    public static final String getByAccount="/user/getByAccount";
    //The url which can find the user's password.
    public static final String forgetPassword="/user/forgetUserPassword";
    //The url which can list the follow user's recipes.
    public static final String listByFollow="/recipes/listByFollow";
    //The url which can get all recipe type.
    public static final String showAllType="/type/typeList";
    //The url which can show recipes according to type.
    public static final String showByType="/recipes/getByType";
    //The url which can show all recipes.
    public static final String showAllRecipes="/recipes/listAll";
    public static final String showRecommendRecipes="/recipes/recommend";
    //The url which can get the recipe's detail.
    public static final String recipesDetail="/recipes/selectById2";
    //The url which can add a recipe to scan history.
    public static final String addScan="/scan/addScan";
    //The url which can get a recipe's comment list.
    public static final String getCommentList="/comment/commentListByRid";
    //The url which can add a comment.
    public static final String addComment="/comment/addComment";
    //The url which can add a recipe to collection.
    public static final String collection="/collection/collect";
    //The url which can show my collection recipes.
    public static final String myCollection="/collection/listByUid";
    //The url which can get the user.
    public static final String GetUser="/user/selectById";
    //The url which can show my recipes.
    public static final String myRecipes="/recipes/listByUser";
    //The url which can get the user message.
    public static final String getUserMsg="/user/getUserMsg";
    //The url which can follow a user.
    public static final String follow="/follow/add";
    //The url which can get a user's fans.
    public static final String getFansByUser="/follow/getFansByUser";
    //The url which can get a user's follows.
    public static final String getFollowByUser="/follow/getFollowByUser";
    //The url which can show the recipes in the scan history.
    public static final String listByScan="/scan/listByUid";
    //The url which can get all recipe type.
    public static final String getAllType="/type/typeList";
    //The url which can add a recipe.
    public static final String addRecipes="/recipes/addRecipes";
    //The url which can upload image.
    public static final String upload_URL="/recipes/uploadImg";
    //The url which can show recipes by searching keywords.
    public static final String recipesSearch="/recipes/recipesSearch";
    public static final String UpdateSelf="/user/editUser";
}
