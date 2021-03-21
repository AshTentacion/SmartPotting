package com.example.demo.service.serviceImpl;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.DeviceMapper;
import com.example.demo.dao.PlantDataMapper;
import com.example.demo.domain.Device;
import com.example.demo.domain.PlantData;
import com.example.demo.service.IndexService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    PlantDataMapper plantDataMapper;

    @Autowired
    DeviceMapper deviceMapper;

    @Override
    public List<PlantData> getDataByDeviceId(String device_id) {
        // TODO Auto-generated method stub
        return plantDataMapper.getAllByDeviceId(device_id);
    }

    @Override
    public int insertPlantData(JSONObject plantData) {
        // TODO Auto-generated method stub
        System.out.println(plantData.toString());
        JSONObject sensor_data = JSONObject.parseObject(plantData.get("sensor_data").toString());
        PlantData data = new PlantData();
        data.setDeviceId(plantData.get("client_id").toString());
        data.setHumidity(Float.parseFloat(sensor_data.get("humidity").toString()));
        data.setIllumination(Float.parseFloat(sensor_data.get("illumination").toString()));
        data.setPlantStatus(Boolean.parseBoolean(sensor_data.get("plant_status").toString()));
        data.setPressure(Float.parseFloat(sensor_data.get("pressure").toString()));
        data.setTemperature(Float.parseFloat(sensor_data.get("temperature").toString()));
        data.setWaterLevel(Float.parseFloat(sensor_data.get("water_level").toString()));
        data.setThreshold(Integer.parseInt(sensor_data.get("threshold").toString()));
        return plantDataMapper.insertSelective(data);
    }

    @Override
    public int isHasDeviceAndInsert(String device_id, Integer user_id) {
        // TODO Auto-generated method stub
        int count = deviceMapper.selectCountByDeviceIdUserId(device_id, user_id);
        if(count>0){
            return 0;
        }
        count = plantDataMapper.isHasDevice(device_id);
        if(count>0){
            Device device = new Device();
            device.setDeviceId(device_id);
            device.setName("智能盆栽");
            device.setType((byte) '0');
            device.setUserId(user_id);
            int res = deviceMapper.insertSelective(device);
            if(res>0){
                return 1;
            }else{
                return 2;
            }
        }
        return 3;
    }

    @Override
    public List<Device> selectAllByUserId(Integer user_id) {
        // TODO Auto-generated method stub
        List<Device> list = deviceMapper.selectAllByUserId(user_id);
        return list;
    }
    
}
