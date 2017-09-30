package com.jinchi.controller.backend;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jinchi.config.holder.AdminInfoHolder;
import com.jinchi.model.Admin;
import com.jinchi.service.AdminService;

/**
 * @author yuxuanjiao
 * @date 2017年9月30日 下午4:13:31 
 * @version 1.0
 */

@Controller
@RequestMapping("/backend")
public class BaseController {

    @Resource 
   private AdminService adminService;
    
    @RequestMapping({"/", "", "index"})
    public String index() {
        return "/index/upload";
    }
    
    @RequestMapping("/loginAction")
    public String login(Model model, Admin admin, HttpSession session) {
        Admin res = adminService.checkUser(admin);
        if (res == null) {
            model.addAttribute("error", "* 用户名不存在");
            return "/index/login";
        } else if (!admin.getPasswd().equals(res.getPasswd())) {
            model.addAttribute("error", "* 密码错误");
            return "/index/login";
        } else {
            session.setAttribute("admin", res);
            return "redirect:/backend/";
        }
    }

    @RequestMapping("/login")
    public String loginIndex(HttpSession session) {
        if (session.getAttribute("admin") != null) {
            return "redirect:/backend/";
        }
        return "/index/login";
    }
    
    @RequestMapping("/test")
    public String upload() {
        return "/index/upload";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        AdminInfoHolder.clear();
        return "/index/login";
    }
}
