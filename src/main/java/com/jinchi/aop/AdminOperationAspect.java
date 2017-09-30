package com.jinchi.aop;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jinchi.config.handler.exception.UserLoginException;
import com.jinchi.config.holder.AdminInfoHolder;
import com.jinchi.model.Admin;
import com.jinchi.service.AdminOperationService;

/**
 * @author yuxuanjiao
 * @date 2017年9月30日 下午5:08:25
 * @version 1.0
 */

@Component
@Aspect
public class AdminOperationAspect {

    @Resource
    private AdminOperationService adminOperationService;

    @Pointcut("@annotation(com.jinchi.annotation.AdminOperation)")
    public void adminOperation() {
    }

    @Around("adminOperation()")
    public Object watchAdminOperation(ProceedingJoinPoint jp) {
        Object obj = null;
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        Admin admin = (Admin) httpServletRequest.getSession().getAttribute("admin");
        if (admin == null) {
            throw new UserLoginException();
        } else {
            AdminInfoHolder.setAdmin(admin);
            System.out.println("before....");
        }

        try {
            obj = jp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        adminOperationService.process(jp);
        return obj;
    }
}
