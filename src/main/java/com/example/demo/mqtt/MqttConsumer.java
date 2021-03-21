package com.example.demo.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

//import com.example.demo.mqtt.MqttConfigBean;

@Component
public class MqttConsumer implements ApplicationRunner {

    private static MqttClient client;
	    
	private static MqttTopic mqttTopic;

    @Autowired
    public MqttConfigBean mqttConfigBean;
    

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // TODO Auto-generated method stub
        this.connect();
    }

    public void reConnect() throws Exception {
        if(null != client) {
            this.connect();
        }
    }

    public void subscribe(String topic,int qos){
        try {
            System.out.println("topic:"+topic);
            client.subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public MqttClient getClient() {
        return client;
    }

    public void setClient(MqttClient client) {
        this.client = client;
    }

    public MqttTopic getMqttTopic() {
        return mqttTopic;
    }

    public void setMqttTopic(MqttTopic mqttTopic) {
        this.mqttTopic = mqttTopic;
    }

    public void publish(String topic, String pushMessage) {
        publish(0, false, topic, pushMessage);
    }

    public void publish(int qos, boolean retained, String topic, String pushMessage) {
        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        message.setRetained(retained);
        message.setPayload(pushMessage.getBytes());
        MqttTopic mTopic = client.getTopic(topic);
        if (null == mTopic) {
            System.out.println("topic err");
        }
        MqttDeliveryToken token;
        try {
            token = mTopic.publish(message);
            token.waitForCompletion();
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void connect() throws Exception{ 
        client = new MqttClient(mqttConfigBean.getHostUrl(), mqttConfigBean.getClientId(), new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();                                      
        options.setCleanSession(false);  
        //options.setUserName(mqttConfigBean.getUsername());  
        //options.setPassword(mqttConfigBean.getPassword().toCharArray());  
        options.setCleanSession(false);   //是否清除session
        // 设置超时时间  
        options.setConnectionTimeout(30);  
        // 设置会话心跳时间  
        options.setKeepAliveInterval(20);  
        try {  
            String[] msgtopic = mqttConfigBean.getMsgTopic();
             //订阅消息
              
            int[] qos = new int[msgtopic.length];
            for (int i = 0; i < msgtopic.length; i++) {
                System.out.println("订阅的主题:"+msgtopic[i]);
                qos[i]=0;
            }
            client.setCallback(new TopMsgCallback(client,options,msgtopic,qos));  
            client.connect(options);
            client.subscribe(msgtopic,qos);
            System.out.println("MQTT连接成功:"+mqttConfigBean.getClientId()+":"+client);
        } catch (Exception e) {  
            System.out.println("MQTT连接异常："+e);
        }  
    }  
    
}
