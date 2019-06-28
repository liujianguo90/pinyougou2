package com.pinyougou.page.listener;
import com.alibaba.fastjson.JSON;
import com.pinyougou.page.service.ItemPageService;
import entity.EsItem;
import entity.MessageInfo;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;
public class MessageListener implements MessageListenerConcurrently {
    @Autowired
    private ItemPageService itemPageService;
    @Value("${pagedir}")
    private String pagedir;
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        try {
            for (MessageExt msg : msgs) {
                //读取消息
              String json = new java.lang.String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET);
              //将消息转化成为对象
                MessageInfo info = JSON.parseObject(json, MessageInfo.class);
                //如果是生成页面
                if(info.getMethod()==MessageInfo.METHOD_ADD){
                    List<EsItem> esItems = JSON.parseArray(info.getContext().toString(), EsItem.class);
                    //生成静态页面
                    for (EsItem esItem : esItems) {
                        itemPageService.genItemHtml(esItem.getGoodsId());
                    }
                }else if(info.getMethod()==MessageInfo.METHOD_DELETE){
                    List<Long> idArray = JSON.parseArray(info.getContext().toString(), Long.class);
                    //删除静态页面
                    for (Long goodsId : idArray) {
                        //删除文件
                       itemPageService.deleteHtmlPage(goodsId);
                    }
                }
            }
        } catch (Exception e) {
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
