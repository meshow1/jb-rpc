package com.jiangbo.jbrpc;

import lombok.Data;


/*
*
* 表示一个响应
*
* */

@Data
public class Response {
    /*
    * 服务返回编码，0成功，非0失败
    * */
    private int code = 0;//表示是否成功调用
    private String message = "OK";//若调用失败原因信息
    private Object data;//返回的数据
}
