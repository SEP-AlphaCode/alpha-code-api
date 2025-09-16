package com.alphacode.alphacodeapi.service;

import org.eclipse.paho.client.mqttv3.MqttException;

public interface MqttService {
    void publish(String topic, String payload) throws MqttException;
}