package com.jinchi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yuxuanjiao
 * @date 2017年8月5日 下午9:31:33 
 * @version 1.0
 */
@Controller
public class ErrorController {
    
    @RequestMapping("/400")
    public String page400(Model model) {
        model.addAttribute("errorCode", 400);
        return "/index/error";
    }
    @RequestMapping("/404")
    public String page404(Model model) {
        model.addAttribute("errorCode", 404);
        return "/index/error";
    }
    @RequestMapping("/401")
    public String page401(Model model) {
        model.addAttribute("errorCode", 401);
        return "/index/error";
    }
    @RequestMapping("/500")
    public String page500(Model model) {
        model.addAttribute("errorCode", 500);
        return "/index/error";
    }
    
}
