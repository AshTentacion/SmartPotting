package com.example.demo.mqtt;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.IndexService;
import com.example.demo.service.serviceImpl.IndexServiceImpl;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.ApplicationContext;

public class TopMsgCallback implements MqttCallback {
    
    private MqttClient client;
	private MqttConnectOptions options;
	private String[] topic;
    private int[] qos;
    
    private static ApplicationContext applicationContext;
    IndexService indexService;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        TopMsgCallback.applicationContext = applicationContext;
    }
     
    
    public TopMsgCallback() {}
     
    public TopMsgCallback(MqttClient client, MqttConnectOptions options,String[] topic,int[] qos) {
        this.client = client;
        this.options = options;
        this.topic=topic;
        this.qos=qos;
    }

    public void connectionLost(Throwable cause) {  
        System.out.println("MQTT连接断开，发起重连");
        while(true) {
            try {
                Thread.sleep(30000);
                client.connect(options);
               //订阅消息  
                client.subscribe(topic,qos); 
                System.out.println("MQTT重新连接成功:"+client);
                break;
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }    
    } 

    public void deliveryComplete(IMqttDeliveryToken token) {}

    public void messageArrived(String topic, MqttMessage message) throws Exception {
        indexService = applicationContext.getBean(IndexServiceImpl.class);
        System.out.println();
        //订阅消息字符
        String msg=new String(message.getPayload());
        byte[] bymsg=getBytesFromObject(msg);
        System.out.println("topic:"+topic);
        System.out.println("msg:"+msg);
        try {
            switch(topic){
                case "plant_data":
                    JSONObject plantData = JSON.parseObject(msg);
                    if(indexService.insertPlantData(plantData)>0){
                        System.out.println("插入盆栽数据成功");
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
   }

    public  byte[] getBytesFromObject(Serializable obj) throws Exception {
        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);
        return bo.toByteArray();
    }
}
