package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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
@Transactional
@RequestMapping("/user") //表示命名的空间
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/initial")
    @ResponseBody
    public void initial(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            //若当前用户存在，则判断是否为普通管理员或者超级管理员
            if (user != null) {
                response.setContentType("text/html;charset=utf-8");
                if (user.getType() == 1) {// 普通管理员
                    ((HttpServletResponse) response).sendRedirect("/index2.html");

                } else {// 超级管理员
                    ((HttpServletResponse) response).sendRedirect("/index.html");

                }
            } else {
                // 重定向页面跳转
                ((HttpServletResponse) response).sendRedirect("/login.html");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(int type, String account, String password) {
        // Map是以键值形式存储数据，有点类似于数组。string是它的键，存储的类型为String
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        System.out.println(account + "===" + password);
        User user = userService.selectByName(account);
        System.out.println(user);
        Map<String, Object> map = new HashMap<String, Object>();
        if (user != null) {
            if (type > 0) {// web端
                if (user.getPassword().equals(password)) {
                    if (user.getType() > 0) {
                        map.put("code", 0);
                        map.put("msg", "登录成功");
                        map.put("data", user);
                        request.getSession().setAttribute("user", user);
                    } else {
                        map.put("code", 1);
                        map.put("msg", "您没有访问权限");
                    }
                } else {
                    map.put("code", 1);
                    map.put("msg", "用户名或密码错误");
                }
            } else {
                if (user.getPassword().equals(password)) {
                    map.put("code", 0);
                    map.put("msg", "登录成功");
                    map.put("data", user);
                    request.getSession().setAttribute("user", user);
                } else {
                    map.put("code", 1);
                    map.put("msg", "用户名或密码错误");
                }
            }
        } else {
            map.put("code", 1);
            map.put("msg", "用户不存在");
        }
        return map;
    }

    @RequestMapping(value = "/userLogout")
    @ResponseBody
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        response.setContentType("text/html;charset=utf-8");
        try {
            ((HttpServletResponse) response).sendRedirect("/login.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/userTypeList")
    @ResponseBody
    public Map<String, Object> list(int type, int page, int limit) {
        try {
            Map<String, Object> map = userService.selectAllByTypeCount(type, page, limit);
            map.put("code", 0);
            map.put("msg", "success");
            return map;
        } catch (Exception e) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", 1);
            map.put("msg", "服务器繁忙");
            map.put("data", "[]");
            map.put("count", 0);
            e.printStackTrace();
            return map;
        }

    }

    @RequestMapping(value = "/selectById")
    @ResponseBody
    public Map<String, Object> selectById(int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            User user = userService.selectByPrimaryKey(id);
            map.put("code", 0);
            map.put("msg", "success");
            map.put("data", user);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 1);
            map.put("msg", "操作失败");
        }
        return map;
    }

    @RequestMapping(value = "/getUserMsg")
    @ResponseBody
    public Map<String, Object> getUserMsg(int id,int myUid) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            User bean = userService.getUserMsg(id,myUid);
            map.put("code", 0);
            map.put("msg", "success");
            map.put("data", bean);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 1);
            map.put("msg", "操作失败");
        }
        return map;
    }

    @RequestMapping(value = "/getByAccount", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getByAccount( String account) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            User user = userService.selectByName(account);
            map.put("code", 0);
            map.put("msg", "success");
            map.put("data", user);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 1);
            map.put("msg", "操作失败");
        }
        return map;
    }

    @RequestMapping(value = "/editUser")
    @ResponseBody
    public Map<String, Object> edit(User u) {
        System.out.println(u);
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            User user = userService.selectByName(u.getAccount());
            if (user != null && user.getId() != u.getId()) {
                map.put("code", 1);
                map.put("msg", "用户名已存在");
                return map;
            }
            int i = userService.updateUser(u);
            if (i != 0) {
                map.put("code", 0);
                map.put("msg", "success");
            } else {
                map.put("code", 1);
                map.put("msg", "操作失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 1);
            map.put("msg", "操作失败");
        }
        return map;
    }

    @RequestMapping(value = "/deleteUser")
    @ResponseBody
    public Map<String, Object> delete(int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            int i = userService.deleteUser(id);
            if (i != 0) {
                map.put("code", 0);
                map.put("msg", "success");
            } else {
                map.put("code", 1);
                map.put("msg", "操作失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 1);
            map.put("msg", "操作失败");
        }
        return map;
    }

    @RequestMapping(value = "/addUser")
    @ResponseBody
    public Map<String, Object> add(User u) {
        System.out.println(u);
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            User user = userService.selectByName(u.getAccount());
            if (user != null) {
                map.put("code", 1);
                map.put("msg", "用户名已存在");
                return map;
            }
            int i = userService.addUser(u);
            System.out.println(u);
            if (i != 0) {
                map.put("code", 0);
                map.put("msg", "success");
            } else {
                map.put("code", 1);
                map.put("msg", "操作失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 1);
            map.put("msg", "操作失败");
        }
        return map;
    }

    @RequestMapping(value = "/getUserSelf")
    @ResponseBody
    public Map<String, Object> getUserSelf(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        if (user != null) {
            try {
                User u = userService.selectByPrimaryKey(user.getId());
                map.put("code", 0);
                map.put("msg", "success");
                map.put("data", u);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("code", 1);
                map.put("msg", "获取失败");
            }
            return map;
        }
        else {
            map.put("code", 2);
            map.put("msg", "登录失效");
            return map;
        }
    }

    @RequestMapping(value = "/editUserSelf")
    @ResponseBody
    public Map<String, Object> updateSelf(User u, HttpServletRequest request) {
        System.out.println(u);
        User user = (User) request.getSession().getAttribute("user");
        Map<String, Object> map = new HashMap<String, Object>();
        if (user != null) {
            try {
                u.setId(user.getId());
                u.setAccount(user.getAccount());
                u.setPassword(user.getPassword());
                u.setType(user.getType());
                map.put("code", 0);
                map.put("msg", "success");
                map.put("data", userService.updateUser(u));
            } catch (Exception e) {
                e.printStackTrace();
                map.put("code", 1);
                map.put("msg", "修改失败");
            }
            return map;
        }
        else {
            map.put("code", 2);
            map.put("msg", "登录失效");
            return map;
        }
    }

    @RequestMapping(value = "/editUserPassword")
    @ResponseBody
    public Map<String, Object> editUserPassword(String initialpwd, String password,
                                              HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            if (initialpwd.equals(user.getPassword())) {
                try {
                    user.setPassword(password);
                    int i = userService.editUserPassword(user);
                    if (i != 0) {
                        map.put("code", 0);
                        map.put("msg", "success");
                    } else {
                        map.put("code", 1);
                        map.put("msg", "修改失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    map.put("code", 1);
                    map.put("msg", "修改失败");
                }
                return map;
            }
            else {
                map.put("code", 1);
                map.put("msg", "原密码错误");
                return map;
            }
        }
        else {
            map.put("code", 2);
            map.put("msg", "登录失效");
            return map;
        }
    }

    @RequestMapping(value = "/forgetUserPassword")
    @ResponseBody
    public Map<String, Object> forgetUserPassword(User user) {
        System.out.println(user);
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            int flag = userService.editUserPassword(user);
            if (flag != 0) {
                map.put("code", 0);
                map.put("msg", "success");
            } else {
                map.put("code", 1);
                map.put("msg", "操作失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 1);
            map.put("msg", "操作失败");
        }
        return map;
    }
    /*
     **登录成功响应方法
     * @param message
     * @author lgf
     * */
//    @RequestMapping(value="/success",method = {RequestMethod.POST,RequestMethod.GET})
//    private String ok(){
//        return "success";
//    }

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
//    @RequestMapping(value="/loginPage" , method = {RequestMethod.POST,RequestMethod.GET})
//    private ModelAndView login(HttpServletRequest request, HttpSession session){
//        ModelAndView mav=new ModelAndView();
//        out.print("ajax进入后台!!\n");
//        String account = request.getParameter( "account") ;
//        String password = request.getParameter( "password");
//
//        User now = userService.loginPage(account,password);
//        User now_type = userService.userType(account,password);
//        //测试now是否为null
//        out.print(now);
//
//        if (now == null){
//            mav.clear();
//            mav.setViewName("login");
//            return mav ;
//        }else {
//            session.setAttribute("now", now.getAccount());  //原来是getName
//            //   out.print (now.getName());
//            //验证通过跳转首页
////            mav.setViewName("homePage") ;
//            //判断是否为超级管理员
//            if(now_type.getType()==2){
//                mav.setViewName("index") ;
//                return mav;
//            }
//            else if (now_type.getType()==1) {
//                mav.setViewName("index2");
//                return mav ;
//            }
//            else{
//                mav.setViewName("login");
//                return mav ;
//            }
//        }
//    }
    // 退出登录
//    @RequestMapping(value = "/logout")
//    @ResponseBody
//    public void logout(HttpServletRequest request, HttpServletResponse response) {
//        request.getSession().removeAttribute("now");
//        response.setContentType("text/html;charset=utf-8");
//        try {
//            ((HttpServletResponse) response).sendRedirect(request.getContextPath() + "/user/login");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

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
