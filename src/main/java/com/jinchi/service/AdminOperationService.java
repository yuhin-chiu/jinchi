package com.jinchi.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import com.jinchi.config.holder.AdminInfoHolder;
import com.jinchi.model.Admin;

/**
 * @author yuxuanjiao
 * @date 2017年9月30日 下午5:51:14 
 * @version 1.0
 */

@Service
public class AdminOperationService {

    public void process(ProceedingJoinPoint jp) {
        Admin admin = AdminInfoHolder.getAdmin();
        
        System.out.println("process");
    }
}
