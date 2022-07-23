package com.shf.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MP自动填充功能
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("insertFill createTime updateTime自动填充...");
//        实现填充业务逻辑
        this.strictInsertFill(
                metaObject, // 源数据对象
                "createTime", // 填充对象
                LocalDateTime.class, // 填充类型
                LocalDateTime.now()); // 填充值
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());

//        判断当前对象的自动填充属性是否已经进行了赋值
        Object age = this.getFieldValByName("age", metaObject);
        if (age == null) {
            log.info("insertFill age 自动填充...");
            this.strictInsertFill(metaObject, "age", Integer.class, 33);
        }

//        判断是否有author字段
        boolean hasAuthor = metaObject.hasSetter("author");
        if (hasAuthor) {
            log.info("insert author属性");
            this.strictInsertFill(metaObject, "author", String.class, "shf");
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("updateFill自动填充...");

        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}
