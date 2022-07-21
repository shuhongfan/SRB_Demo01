package com.atguigu.ssr_demo.controller;

import com.atguigu.ssr_demo.entity.Teacher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author helen
 * @since 2020/4/23
 */
@Controller
public class TeacherController {

    @GetMapping("/teacher/list")
    public String list(Model model){

        Teacher teacher = new Teacher(1, "Helen", "高级讲师");
        model.addAttribute("teacher", teacher);
        return "teacher/list";  //视图解析
    }
}
