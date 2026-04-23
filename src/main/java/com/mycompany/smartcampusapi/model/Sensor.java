/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 
Name: Janefa Jeba
Student ID: w2080916

*/

package com.mycompany.smartcampusapi.model;

public class Sensor {

    private String id;
    private String type;
    private Double currentValue;
    private String roomId;
    private String status;

    public Sensor() {
    }

    public Sensor(String id, String type, Double currentValue, String roomId, String status) {
        this.id = id;
        this.type = type;
        this.currentValue = currentValue;
        this.roomId = roomId;
        this.status = status;
    }

    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
