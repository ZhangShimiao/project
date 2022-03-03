package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;


@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("/login")
    private String login() {
        return "login";
    }

    /*
     **登录成功响应方法
     * @param message
     * @author lgf
     * */
    @RequestMapping(value="/success",method = {RequestMethod.POST,RequestMethod.GET})
    private String ok(){
        return "success";
    }

//    写死数据的添加管理员
    @RequestMapping(value="/test",method = {RequestMethod.POST,RequestMethod.GET})
    public String add(){
        User user = new User();
        user.setAccount("aa");
        user.setNickname("aa");
        user.setPassword("111");
        user.setType(1);
        userService.addUser(user);
        System.out.print("成功了");
        return "login";
    }


//测试数据传输到html上
    @RequestMapping("/testthy")
    private ModelAndView test() {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("add");

        modelAndView.addObject("key", "hhhhhhhh");

        //System.out.println("test");

        return modelAndView;

    }
//
//    @RequestMapping(value="/addUser" , method = {RequestMethod.POST,RequestMethod.GET})
//    @ResponseBody
//    private ModelAndView addUser(User record) {
//        ModelAndView mav=new ModelAndView();
//        System.out.println(record);
//        try {
//            User user = userService.selectByName(record.getAccount());
//            if (user != null) {
//                mav.clear();
//                mav.setViewName("add");
//                return mav ;
//            }
//            int flag = userService.addUser(record);
//            System.out.println(record);
//            if (flag == 0) {
//                mav.setViewName("add");
//                return mav ;
//            } else {
//                mav.setViewName("login") ;
//                return mav;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return mav;
//    }

//    输入账号密码登录验证
    @RequestMapping(value="/loginPage" , method = {RequestMethod.POST,RequestMethod.GET})
    private ModelAndView login(HttpServletRequest request, HttpSession session){
        ModelAndView mav=new ModelAndView();
        out.print("ajax进入后台!!\n");
        String account = request.getParameter( "account") ;
        String password = request.getParameter( "password");

        User now = userService.loginPage(account,password);
        User now_type = userService.userType(account,password);
        //测试now是否为null
        out.print(now);

        if (now == null){
            mav.clear();
            mav.setViewName("login");
            return mav ;
        }else {
            session.setAttribute("now", now.getAccount());  //原来是getName
            //   out.print (now.getName());
            //验证通过跳转首页
//            mav.setViewName("homePage") ;
            //判断是否为超级管理员
            if(now_type.getType()==2){
                mav.setViewName("index") ;
                return mav;
            }
            else if (now_type.getType()==1) {
                mav.setViewName("index2");
                return mav ;
            }
            else{
                mav.setViewName("login");
                return mav ;
            }
        }
    }
    // 退出登录
    @RequestMapping(value = "/logout")
    @ResponseBody
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("now");
        response.setContentType("text/html;charset=utf-8");
        try {
            ((HttpServletResponse) response).sendRedirect(request.getContextPath() + "/user/login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    @Autowired
//    UserService userService;
//
//    @GetMapping(value = {"/", "login"})
//    public String loginPage() {
//        return "hello";
//    }
//
//    @PostMapping("login2")
//    public String loginSuccess(User user, HttpSession session, Model model) {
//        try {
//            //先查找一下有没有该账号
//            User userName = userService.findUserName(user.getAccount());
//            System.out.println(userName);
//            if (userName != null) {
//                //如果有账号则判断账号密码是否正确
//                String password = userService.findPassword(user.getAccount());
//                if (password.equals(user.getPassword())) {
//                    //添加到session保存起来
//                    session.setAttribute("loginUser", user);
//                    return "redirect:/success.html";
//                } else {
//                    //如果密码错误，则提示输入有误
//                    model.addAttribute("msg", "账号或者密码有误");
//                    System.out.println("hhhhh");
//                    return "hello";
//                }
//            } else {
//                model.addAttribute("msg", "账号或者密码有误");
//                System.out.println("llllll");
//                return "hello";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return e.getMessage();
//        }
//    }
//
//    @GetMapping("success.html")
//    public String successPage(HttpSession session, Model model) {
//        Object loginUser = session.getAttribute("loginUser");
//        if (loginUser != null) {
//            return "success";
//        } else {
//            model.addAttribute("msg", "请登录");
//            System.out.println("222222");
//            return "hello";
//        }
//    }

}
