package com.example.demo.dao;

import java.util.List;

import com.example.demo.domain.PlantData;

public interface PlantDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlantData record);

    int insertSelective(PlantData record);

    PlantData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlantData record);

    int updateByPrimaryKey(PlantData record);

    int isHasDevice(String device_id);

    List<PlantData> getAllByDeviceId(String device_id);
}