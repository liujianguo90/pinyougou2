package entity;

import java.io.Serializable;

/**
 * RocketMQ消息包装对象
 */
public class MessageInfo implements Serializable {

    //1|2|3->增加|修改|删除
    public static final int METHOD_ADD=1;
    public static final int METHOD_UPDATE=2;
    public static final int METHOD_DELETE=3;

    //method:1|2|3->增加|修改|删除
    private int method;

    //要发送的内容
    private Object context;

    //设置主题

    private String topic;

    //设置标签
    //@JSONField(serialize = false)
    private String tags;

    //设置keys

    private String keys;

    //带参构造函数
    public MessageInfo(int method, Object context) {
        this.method = method;
        this.context = context;
    }

    public MessageInfo(String topic, String tags, String keys) {
        this.topic = topic;
        this.tags = tags;
        this.keys = keys;
    }

    public MessageInfo(int method, Object context, String topic, String tags, String keys) {
        this.method = method;
        this.context = context;
        this.topic = topic;
        this.tags = tags;
        this.keys = keys;
    }

    public MessageInfo() {
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public Object getContext() {
        return context;
    }

    public void setContext(Object context) {
        this.context = context;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }
}
