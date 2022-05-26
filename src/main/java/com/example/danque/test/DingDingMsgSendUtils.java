package com.example.danque.test;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

/**
 * @Author: yansf
 * @Description:钉钉消息发送工具类
 * @Date: 10:44 AM 2019/6/12
 * @Modified By:
 */
@Slf4j
@Component
public class DingDingMsgSendUtils {


    /**
     * 处理发送的钉钉消息
     *
     * @param accessToken
     * @param textMsg
     */
    private static void dealDingDingMsgSend(String accessToken, String textMsg) {
        log.info("accessToken:" + accessToken + ", textMsg:" + textMsg);
        HttpClient httpclient = HttpClients.createDefault();
        String WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=" + accessToken;
        HttpPost httppost = new HttpPost(WEBHOOK_TOKEN);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
        StringEntity se = new StringEntity(textMsg, "utf-8");
        httppost.setEntity(se);
        try {
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                log.info("【发送钉钉群消息】消息响应结果：" + JSON.toJSONString(result));
            }
        } catch (Exception e) {
            log.error("【发送钉钉群消息】error：" + e.getMessage(), e);
        }
    }

    /**
     * 发送钉钉群消息
     *
     * @param accessToken
     * @param content
     */
    public static void sendDingDingGroupMsg(String accessToken, String content) {
        String textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"" + content + "\"}}";
        dealDingDingMsgSend(accessToken, textMsg);
    }

}
