package com.pinyougou.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;


import com.pinyougou.pojo.TbUser;
import com.pinyougou.pojo.Tborders;
import com.pinyougou.pojo.UserInfo;

import com.pinyougou.pojo.entity.Result;
import com.pinyougou.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import util.FastDFSClient;

import java.util.List;

/**
 * controller
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;//文件服务器地址

    String url = null;

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody UserInfo userInfo) {
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            userInfo.getTbAddress().setUserId(name);
            userInfo.getUser().setHeadPic(url);
            userService.add(userInfo);
            return new Result(true, "注册成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "注册失败");
        }
    }

    @RequestMapping("/upload")
    public Result upload(MultipartFile file) {
        //1、取文件的扩展名
        String originalFilename = file.getOriginalFilename();
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        try {
            //2、创建一个FastDFS的客户端
            FastDFSClient fastDFSClient
                    = new FastDFSClient("classpath:fdfs_client.conf");

            //3、执行上传处理
            String path = fastDFSClient.uploadFile(file.getBytes(), extName);
            //4、拼接返回的url和ip地址，拼装成完整的url
            url = IMAGE_SERVER_URL + path;
            System.out.println(url);
            return new Result(true, "上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "上传失败");
        }
    }


    /**
     * 获取登录人的账号
     */
    @RequestMapping("/getUser")
    public TbUser getName() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUser(name);

    }

    /**
     * 获取订单信息
     *
     * @return
     */
    @RequestMapping("/getOrderInfo")
    public List<Tborders> getOrderInfo() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getOrderInfo(name);
    }




}
