package com.alphacode.alphacodeapi.service.impl;

import com.alphacode.alphacodeapi.service.MqttService;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import java.util.UUID;

@Slf4j
@Service
public class MqttServiceImpl implements MqttService {

    private IMqttClient client;

    @Value("${mqtt.broker:tcp://localhost:1883}")
    private String brokerUrl;

    @Value("${mqtt.clientId:spring-device-service}")
    private String clientId;

    @PostConstruct
    public void init() {
        try {
            client = new MqttClient(brokerUrl, clientId + "-" + UUID.randomUUID());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            client.connect(options);
            log.info("✅ MQTT connected to broker: {}", brokerUrl);

            // ví dụ: subscribe để test phản hồi từ thiết bị
            client.subscribe("devices/+/status", (topic, msg) -> {
                String payload = new String(msg.getPayload());
                log.info("📥 MQTT received -> topic: {}, payload: {}", topic, payload);
            });

        } catch (Exception e) {
            log.error("❌ Failed to connect MQTT broker", e);
        }
    }

    @PreDestroy
    public void cleanup() {
        try {
            if (client != null && client.isConnected()) {
                client.disconnect();
                client.close();
            }
        } catch (Exception e) {
            log.error("Error closing MQTT client", e);
        }
    }

    @Override
    public void publish(String topic, String payload) {
        try {
            if (client == null || !client.isConnected()) {
                log.warn("⚠️ MQTT client not connected, reconnecting...");
                init();
            }

            MqttMessage message = new MqttMessage(payload.getBytes());
            message.setQos(1);
            client.publish(topic, message);

            log.info("📤 Published MQTT -> topic: {}, payload: {}", topic, payload);
        } catch (Exception e) {
            log.error("❌ Failed to publish MQTT message", e);
            throw new RuntimeException("Failed to publish MQTT message", e);
        }
    }
}

