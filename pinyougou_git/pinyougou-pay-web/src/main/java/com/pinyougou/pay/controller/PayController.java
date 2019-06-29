package com.pinyougou.pay.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pay.service.PayService;

import com.pinyougou.pojo.entity.Result;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.IdWorker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * 支付控制层
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/pay")
public class PayController {
    @Reference
    private PayService payService;

    /**
     * 生成二维码
     *
     * @return
     */
    @CrossOrigin(origins = "http://localhost:9106",allowCredentials = "true")
    @RequestMapping("/createNative")
    public Map createNative(HttpServletResponse response, HttpServletRequest request) {
        //response.setHeader("Access-Control-Allow-Origin", "*");//统一指定的域访问我的服务器资源
        //response.setHeader("Access-Control-Allow-Credentials", "true");//同意客户端携带cookie

        IdWorker idworker = new IdWorker(0, 1);
        return payService.createNative(idworker.nextId() + "", "1");
    }

    @CrossOrigin(origins = "http://localhost:9106",allowCredentials = "true")
    @RequestMapping("/queryPayStatus")
    public Result queryPayStatus(String out_trade_no) {
        Result result = new Result(false, "支付錯誤");
        int count = 0;
        while (true) {
            try {
                Map<String, String> map = payService.queryPayStatus(out_trade_no);
                count++;

                Thread.sleep(3000);
                if (count > 100) {
                    return result = new Result(false, "支付超时");
                }
                if (map == null) {
                    break;
                }
                if (map.get("trade_state").equals("SUCCESS")) {
                    //orderService.updateStatus(out_trade_no, map.get("transaction_id"));
                    return result = new Result(true, "支付成功");
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return result;
    }
}

