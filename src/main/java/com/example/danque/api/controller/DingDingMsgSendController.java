package com.example.danque.api.controller;

import com.example.danque.test.DingDingMsgSendUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @ClassName:DingDingMsgSendController
 * @author: danque
 * @date: 2022/5/26 14:03
 */
@RestController
@RequestMapping("/dingding")
public class DingDingMsgSendController {

    @Autowired
    private DingDingMsgSendUtils dingDingMsgSendUtils;

    /**
     *发送钉钉告警信息
     */
    @GetMapping("/sendDingDingGroupMsg")
    public String sendDingDingGroupMsg(@RequestParam("accessToken") String accessToken,@RequestParam("content") String content) {
        dingDingMsgSendUtils.sendDingDingGroupMsg(accessToken,content);
        return "OK";
    }

}
