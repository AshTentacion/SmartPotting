package com.example.demo.mqtt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MqttConfigBean {
    
    @Value("${spring.mqtt.username}")
    private String username;

    @Value("${spring.mqtt.password}")
    private String password;

    @Value("${spring.mqtt.url}")
    private String hostUrl;
    
    @Value("${spring.mqtt.client.id}")
    private String clientId;

    @Value("${spring.mqtt.topic}")
    private String msgTopic;

    @Value("${spring.mqtt.completionTimeout}")
    private int completionTimeout;

    public String getUsername(){
        return this.username;
    }


    public String getPassword(){
        return this.password;
    }

    public String getHostUrl(){
        return this.hostUrl;
    }

    public String getClientId(){
        return this.clientId;
    }

    public String[] getMsgTopic(){
        String[] topic=msgTopic.split(",");
		return topic;
    }

    public int getCompletionTimeout() {
		return this.completionTimeout;
    }
    


}
