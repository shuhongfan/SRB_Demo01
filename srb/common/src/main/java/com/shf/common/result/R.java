package com.shf.common.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义统一返回结果类
 */
@Data
public class R {

    /**
     * 业务响应码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回数据
     */
    private Map<String, Object> data = new HashMap();

    /**
     * 构造器私有
     */
    private R(){}

    /**
     * 返回成功
     */
    public static R ok(){
        R r = new R();
        r.setCode(ResponseEnum.SUCCESS.getCode());
        r.setMessage(ResponseEnum.SUCCESS.getMessage());
        return r;
    }

    /**
     * 返回失败
     */
    public static R error(){
        R r = new R();
        r.setCode(ResponseEnum.ERROR.getCode());
        r.setMessage(ResponseEnum.ERROR.getMessage());
        return r;
    }

    /**
     * 设置特定结果
     */
    public static R setResult(ResponseEnum responseEnum){
        R r = new R();
        r.setCode(responseEnum.getCode());
        r.setMessage(responseEnum.getMessage());
        return r;
    }

    /**
     * 设置特定消息
     * @param message
     * @return
     */
    public R message(String message){
        this.setMessage(message);
        return this;
    }

    /**
     * 设置特定返回值
     * @param code
     * @return
     */
    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    /**
     * 设置特定数据
     * @param key
     * @param value
     * @return
     */
    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    /**
     *
     * @param map
     * @return
     */
    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}