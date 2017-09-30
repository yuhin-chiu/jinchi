package com.jinchi.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jinchi.annotation.AdminOperation;
import com.jinchi.model.ApiResponse;

/**
 * @author yuxuanjiao
 * @date 2017年9月30日 下午4:10:58
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
public class IndexController extends AbstractController {

    @RequestMapping({ "/", "" })
    public ApiResponse index() {
        return ApiResponse.successResponse().setData("nihao");
    }

    @AdminOperation
    @RequestMapping({ "/test" })
    public ApiResponse test() {
        System.out.println(222);
        return ApiResponse.successResponse().setData("test");
    }

    @RequestMapping({ "/test2" })
    public ApiResponse test2() {
        return ApiResponse.successResponse().setData("test2");
    }

    @RequestMapping({ "/*" })
    public ApiResponse other() {
        return ApiResponse.noMapping();
    }
}
