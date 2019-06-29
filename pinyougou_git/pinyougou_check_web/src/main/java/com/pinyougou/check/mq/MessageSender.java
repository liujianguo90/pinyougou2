package com.pinyougou.check.mq;

import com.alibaba.fastjson.JSON;
import entity.MessageInfo;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;

@Component
public class MessageSender {
    //注入rocketmq对象
    @Autowired
    private DefaultMQProducer producer;
    //定义一个用来发送信息的方法
    public void sendMessage(MessageInfo info){
        try {
            String json = JSON.toJSONString(info);
            Message message = new Message(
                    info.getTopic(),
                    info.getTags(),
                    info.getKeys(),
                    json.getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            //发送消息
            producer.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
