package com.cskj.crawar.controller;

import com.cskj.crawar.entity.ssq.User;
import com.cskj.crawar.service.TaskService;
import com.cskj.crawar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;


    @RequestMapping(value = "/addUser")
    public String addUser(@RequestParam("name") String name, @RequestParam("age") String age) {
        int num = userService.addUser(name, age);
        if (num == 1)
            return "redirect:/allUser";
        else
            return "Insert Error";
    }

    @RequestMapping(value = "/findUser")
    @ResponseBody
    public User findUser(@RequestParam("id") String id) {
        taskService.findAll();
        return userService.findById(id);
    }

    @RequestMapping(value = "/updateById")
    public String updateById(@RequestParam("id") String id, @RequestParam("name") String name) {
        try {
            userService.updataById(id, name);
        } catch (Exception e) {
            return "error";
        }
        return "redirect:/allUser";
    }

    @RequestMapping(value = "/deleteById")
    public String deleteById(@RequestParam("id") String id) {
        try {
            userService.deleteById(id);
        } catch (Exception e) {
            return "error";
        }
        return "redirect:/allUser";
    }

    @RequestMapping(value = "/allUser")
    public String findAllUser(Model model) {
        List<User> userList = userService.findAllUser();
        model.addAttribute("userList", userList);

        StringBuilder builder = new StringBuilder();
        for (User user : userList) {
            if (user != null) {
                builder.append(user.toString()).append("<br>");
            }
        }
        String content = builder.toString();
//        sendMail(content);

        return "index";
    }
}