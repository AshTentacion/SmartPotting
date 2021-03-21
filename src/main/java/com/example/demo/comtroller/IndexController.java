package com.example.demo.comtroller;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.Device;
import com.example.demo.domain.PlantData;
import com.example.demo.mqtt.MqttConsumer;
import com.example.demo.service.IndexService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @Autowired
    IndexService indexService;

    @Autowired
    MqttConsumer mqttConsumer;

    @GetMapping("/index")
    public ModelAndView indexPage() {
        List<Device> list = indexService.selectAllByUserId(0);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/index");
        modelAndView.addObject("Devices", list);
        return modelAndView;
    }

    @GetMapping("/product_data")
    public ModelAndView productData() {
        return new ModelAndView("pages/data");
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        JSONObject sd = JSON.parseObject(
                "{\"device_id\":0, \"humidity\":30.2, \"illumination\":30.2,\"plant_status\":\"false\", \"pressure\":30.2, \"temperature\": 30.2,\"water_level\":30.2}");
        return sd.toJSONString();
    }

    @GetMapping("/api/getById")
    @ResponseBody
    public List<PlantData> getDeviceDataByDeviceId(@RequestParam("id") String device_id) {
        List<PlantData> data_lists = indexService.getDataByDeviceId(device_id);
        System.out.println(data_lists.get(0).getCreateTime().toString());
        return data_lists;
    }

    @GetMapping("/api/watering")
    @ResponseBody
    public JSONObject wateringById(@RequestParam("id") String device_id) {
        JSONObject json = new JSONObject();
        json.put("code", 200);
        json.put("device_id", device_id);
        json.put("operate_type", 2);
        mqttConsumer.publish("set", json.toJSONString());
        return json;
    }

    @GetMapping("/api/set_value")
    @ResponseBody
    public JSONObject setValue(@RequestParam("value") Integer value, @RequestParam("id") String device_id) {
        JSONObject json = new JSONObject();
        json.put("code", 200);
        json.put("threshold", value);
        json.put("device_id", device_id);
        json.put("operate_type", 1);
        System.out.println(json.toJSONString());
        mqttConsumer.publish("set", json.toJSONString());
        return json;
    }

    @GetMapping("/api/insert_device")
    @ResponseBody
    public JSONObject insertDevice(@RequestParam("device_id") String device_id) throws InterruptedException {
        JSONObject json = new JSONObject();
        int res = indexService.isHasDeviceAndInsert(device_id, 0);
        json.put("res", res);
        return json;
    }
}
