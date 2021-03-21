package com.example.demo.dao;

import java.util.List;

import com.example.demo.domain.Device;

public interface DeviceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Device record);

    int insertSelective(Device record);

    Device selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);

    List<Device> selectAllByUserId(Integer user_id);

    int selectCountByDeviceIdUserId(String device_id, Integer user_id);
}