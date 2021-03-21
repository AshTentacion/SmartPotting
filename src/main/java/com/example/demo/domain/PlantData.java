package com.example.demo.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PlantData {
    private Integer id;

    private Float temperature;

    private Float humidity;

    private Float pressure;

    private Float illumination;

    private Float waterLevel;

    private Boolean plantStatus;

    private Integer threshold;

    private String deviceId;

    @JsonFormat(timezone = "UTC", pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }

    public Float getPressure() {
        return pressure;
    }

    public void setPressure(Float pressure) {
        this.pressure = pressure;
    }

    public Float getIllumination() {
        return illumination;
    }

    public void setIllumination(Float illumination) {
        this.illumination = illumination;
    }

    public Float getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(Float waterLevel) {
        this.waterLevel = waterLevel;
    }

    public Boolean getPlantStatus() {
        return plantStatus;
    }

    public void setPlantStatus(Boolean plantStatus) {
        this.plantStatus = plantStatus;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}