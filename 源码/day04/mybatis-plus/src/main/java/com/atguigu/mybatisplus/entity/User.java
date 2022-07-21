package com.atguigu.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "t_user")
public class User {

//    @TableId(type = IdType.ASSIGN_ID)
//    @TableId(type = IdType.AUTO)

//    @TableId
//    private Long uid;

    @TableId(value = "uid")
    private Long id;

    @TableField(value = "username" ) //必须添加
    private String name;

//    @TableField(fill = FieldFill.INSERT)
    private Integer age;
    private String email;

//    @TableField(value = "create_time" ) //多此一举
//    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

//    private Integer deleted;
    @TableLogic
    @TableField(value = "is_deleted")
    private Boolean deleted;  //0 false 未删除;   1 true 已删除

//    @TableLogic
//    @TableField(value = "is_deleted")
//    private Integer deleted;  //1 未删除; -1 已删除
}
