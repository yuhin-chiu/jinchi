package com.jinchi.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jinchi.config.holder.AdminInfoHolder;
import com.jinchi.model.Admin;

/**
 * @author yuxuanjiao
 * @date 2017年8月1日 下午7:51:55
 * @version 1.0
 */

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
            throws Exception {
        Admin admin = (Admin) httpServletRequest.getSession().getAttribute("admin");
        if (admin == null) {
            httpServletResponse.sendRedirect("/backend/login");
            AdminInfoHolder.clear();
            return false;

            /*
             * admin = new Admin(); admin.setUserName("test");
             * httpServletRequest.getSession().setAttribute("admin", admin);
             */
        }
        AdminInfoHolder.setAdmin(admin);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
            ModelAndView modelAndView) throws Exception {
        httpServletRequest.setAttribute("admin", httpServletRequest.getSession().getAttribute("admin"));
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            Object o, Exception e) throws Exception {
        AdminInfoHolder.clear();
    }
}
