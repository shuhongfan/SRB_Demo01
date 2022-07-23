package com.shf.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("user1")
public class User {
    @TableId(value = "uid",type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "username")
    private String name;

    @TableField(fill = FieldFill.INSERT)
    private Integer age;

    private String email;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(value = "is_deleted")
    @TableLogic
    private Boolean deleted;
}
