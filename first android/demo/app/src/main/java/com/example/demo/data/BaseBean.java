package com.example.demo.data;

import java.io.Serializable;

//在java中使用Serializable接口实现对象的序列化
//Serializable在内存序列化是（将数据持久化在磁盘）上开销比较大，
//如Serializable在序列化的时候会产生大量的临时变量，从而引起频繁的GC（垃圾回收）
//参考：https://blog.csdn.net/weixin_40763897/article/details/111009867
//Serializable interface is used to realize object serialization in Java
public class BaseBean implements Serializable {
    //Base entity contains code and message.
    public int code;
    public String msg;
}
