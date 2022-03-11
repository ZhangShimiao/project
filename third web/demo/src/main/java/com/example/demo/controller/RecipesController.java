package com.example.demo.controller;

import com.example.demo.entity.Recipes;
import com.example.demo.entity.Type;
import com.example.demo.entity.User;
import com.example.demo.entity.Collection;
import com.example.demo.service.CollectionService;
import com.example.demo.service.FollowService;
import com.example.demo.service.RecipesService;
import com.example.demo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@Controller
@Transactional
@RequestMapping("/recipes")
public class RecipesController {
    @Autowired
    private RecipesService recipesService;
    @Autowired
    private FollowService followService;
    @Autowired
    private CollectionService collectionService;

    @RequestMapping(value = "/recommend")
    @ResponseBody
    public Map<String, Object> recommend(int uid) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            int tid;
            int[] array = new int[]{};
            List<Recipes> collections=collectionService.selectByUser(uid);
            for(Recipes c:collections) {
                tid= c.getTypeid();
                int[] newArray = new int[array.length + 1];
                for (int i = 0; i < array.length; i++) {
                    newArray[i]=array[i];
                }
                newArray[array.length]=tid;
                array=newArray;
            }
            Map<Integer,Integer> m= new HashMap<Integer,Integer>();
            for (int i = 0; i < array.length; i++) {
                if (m.containsKey(array[i])) {
                    m.put(array[i],m.get(array[i])+1);
                }else{
                    m.put(array[i],1);
                }
            }
            int most=0;
            Iterator iter=m.entrySet().iterator();
            while (iter.hasNext()){
                Map.Entry entry=(Map.Entry)iter.next();
                int key=(Integer)entry.getKey();
                int val=(Integer)entry.getValue();
                if(val>most){
                    most=val;
                }
            }
            Set<Map.Entry<Integer,Integer>> entrySet =m.entrySet();
            List<Recipes> r2 = new ArrayList<>();
            for (Map.Entry<Integer,Integer> en : entrySet) {
                int num=en.getValue();
                List<Recipes> r = new ArrayList<>();
                if (num == most) {
                    System.out.println(en.getKey());
                    r = recipesService.selectByType(en.getKey());
                }
                r2.addAll(r);
            }
            if (r2.size()!=0){
                map.put("code", 0);
                map.put("msg", "success");
                map.put("data", r2);
            }
            else {
                map.put("code", 0);
                map.put("msg", "success");
                map.put("data", recipesService.selectAll());
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 1);
            map.put("msg", "操作失败");
        }
        return map;
    }

    @RequestMapping(value = "/recipesList")
    @ResponseBody
    public Map<String, Object> recipesList(int page, int limit) {
        try {
            Map<String, Object> map = recipesService.selectByPage(page, limit);
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

    @RequestMapping(value = "/recipesSearch")
    @ResponseBody
    public Map<String, Object> recipesSearch(String keyword) {
        try {
            Map<String, Object> map = recipesService.recipesSearch(keyword);
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
    @RequestMapping(value = "/deleteRecipes")
    @ResponseBody
    public Map<String, Object> deleteRecipes(int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            int i = recipesService.deleteRecipes(id);
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

    @RequestMapping("/uploadImg")
    @ResponseBody
    public Map<String, Object> uploadImg(MultipartFile file, HttpSession session) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (file != null && !file.isEmpty()) {
                // 获取上传文件的原始名称
                String initialFilename = file.getOriginalFilename();
                String suffix=initialFilename.substring(initialFilename.lastIndexOf("."));
                // 获取项目部署目录根
                File uploadFile = new File("D:/year4/demo/src/main/resources/static/upload/");
                // 如果保存文件的地址不存在，就先创建目录
                if (!uploadFile.exists()) {
                    uploadFile.mkdirs();
                }
                String newFilename = new Date().getTime() + "" + UUID.randomUUID()+suffix;
                newFilename = newFilename.replace("-", "");
                String url =uploadFile.getPath() +"/" + newFilename;
                // 文件路径url
                System.out.println(url);
                System.out.println(newFilename);
                try {
                    // 使用MultipartFile接口的方法完成文件上传到指定位置
                    file.transferTo(new File(url));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                map.put("code", 0);
                map.put("msg", "上传成功");
                map.put("data", "/upload/" + newFilename);
            } else {
                map.put("code", 1);
                map.put("msg", "上传失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 1);
            map.put("msg", "上传失败");
        }
        return map;
    }

    @RequestMapping(value = "/addRecipes")
    @ResponseBody
    public Map<String, Object> addRecipes(Recipes recipes, HttpServletRequest request) {
        System.out.println(recipes);
        Map<String, Object> map = new HashMap<String, Object>();
        System.out.println(recipes.getUid());
        if(recipes.getUid()<0){
            User user= (User) request.getSession().getAttribute("user");
            if(user==null){
                map.put("code", 2);
                map.put("msg", "登录失效，请重新登录");
                return map;
            }
            recipes.setUid(user.getId());
        }
        try {
            int flag = recipesService.addRecipes(recipes);
            System.out.println(recipes);
            if (flag == 0) {
                map.put("code", 1);
                map.put("msg", "操作失败");
            } else {
                map.put("code", 0);
                map.put("msg", "success");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 1);
            map.put("msg", "操作失败");
        }
        return map;
//        User user= (User) request.getSession().getAttribute("user");
//        System.out.println(user);
//        if(user!=null){
//            recipes.setUid(user.getId());
//            try {
//                int i = recipesService.addRecipes(recipes);
//                System.out.println(recipes);
//                if (i != 0) {
//                    map.put("code", 0);
//                    map.put("msg", "success");
//                } else {
//                    map.put("code", 1);
//                    map.put("msg", "操作失败");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                map.put("code", 1);
//                map.put("msg", "操作失败");
//            }
//            return map;
//        }
//        else {
//            map.put("code", 2);
//            map.put("msg", "登录失效，请重新登录");
//            return map;
//        }
    }

    @RequestMapping(value = "/selectById")
    @ResponseBody
    public Map<String, Object> selectById(int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Recipes recipes = recipesService.selectById(id);
            map.put("code", 0);
            map.put("msg", "success");
            map.put("data", recipes);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 1);
            map.put("msg", "操作失败");
        }
        return map;
    }

    @RequestMapping(value = "/selectById2")
    @ResponseBody
    public Map<String, Object> selectById2(int id,int uid) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Recipes recipes = recipesService.selectById2(id,uid);
            map.put("code", 0);
            map.put("msg", "success");
            map.put("data", recipes);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 1);
            map.put("msg", "操作失败");
        }
        return map;
    }

    @RequestMapping(value = "/editRecipes")
    @ResponseBody
    public Map<String, Object> editRecipes(Recipes recipes,HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user= (User) request.getSession().getAttribute("user");
        if(user!=null){
            recipes.setUid(user.getId());
            try {
                int i = recipesService.editRecipes(recipes);
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
        else {
            map.put("code", 2);
            map.put("msg", "登录失效，请重新登录");
            return map;
        }
    }

//    列出关注列表的人的食谱
    @RequestMapping(value = "/listByFollow")
    @ResponseBody
    public Map<String, Object> listByFollow(int uid) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<User> users=followService.getFollowByUser(uid);
        List<Recipes> beans=new ArrayList<Recipes>();
        for(User u:users) {
            beans.addAll(recipesService.selectByUser(u.getId()));
        }
        try {
            map.put("code", 0);
            map.put("msg", "success");
            map.put("data",beans);
            return map;
        } catch (Exception e) {
            map.put("code", 1);
            map.put("msg", "服务器繁忙");
            map.put("data", "[]");
            map.put("count", 0);
            e.printStackTrace();
            return map;
        }

    }

    @RequestMapping(value = "/getByType")
    @ResponseBody
    public Map<String, Object> getByType(int type) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("code", 0);
            map.put("msg", "success");
            map.put("data", recipesService.selectByType(type));
            return map;
        } catch (Exception e) {
            map.put("code", 1);
            map.put("msg", "服务器繁忙");
            map.put("data", "[]");
            map.put("count", 0);
            e.printStackTrace();
            return map;
        }

    }

    @RequestMapping(value = "/listAll")
    @ResponseBody
    public Map<String, Object> listAll() {

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("code", 0);
            map.put("msg", "success");
            map.put("data", recipesService.selectAll());
            return map;
        } catch (Exception e) {
            map.put("code", 1);
            map.put("msg", "服务器繁忙");
            map.put("data", "[]");
            map.put("count", 0);
            e.printStackTrace();
            return map;
        }

    }
    @RequestMapping(value = "/listByUser")
    @ResponseBody
    public Map<String, Object> listByUser(int uid) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("code", 0);
            map.put("msg", "success");
            map.put("data", recipesService.selectByUser(uid));
            return map;
        } catch (Exception e) {
            map.put("code", 1);
            map.put("msg", "服务器繁忙");
            map.put("data", "[]");
            map.put("count", 0);
            e.printStackTrace();
            return map;
        }
    }
}
