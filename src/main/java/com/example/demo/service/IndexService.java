package com.example.demo.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.Device;
import com.example.demo.domain.PlantData;


public interface IndexService {
    
    public List<PlantData> getDataByDeviceId(String id);

    public int insertPlantData(JSONObject plantData);

    public int isHasDeviceAndInsert(String device_id, Integer user_id);

    public List<Device> selectAllByUserId(Integer user_id);
}
