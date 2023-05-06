package com.example.demo.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EmitLogDirect {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            addToQueue(channel, "severity0", "message 0");
            addToQueue(channel, "severity1", "message 1");
            addToQueue(channel, "severity2", "message 2");
            addToQueue(channel, "severity3", "message 3");
            addToQueue(channel, "severity4", "message 4");
        }
    }

    private static void addToQueue(Channel channel, String severity, String message) throws IOException {
        channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes(StandardCharsets.UTF_8));
        System.out.println(" [x] Sent '" + message + "'");
    }
}