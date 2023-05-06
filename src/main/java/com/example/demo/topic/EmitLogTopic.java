package com.example.demo.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EmitLogTopic {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            addToQueue(channel, "maties.test", "maties.test");
            addToQueue(channel, "test2.mengo", "test2.mengo");
            addToQueue(channel, "aa.contains.bb", "aa.contains.bb");
            addToQueue(channel, "testStart.aaaa", "testStart.aaaa");
            addToQueue(channel, "aaaa.testEnd", "aaaa.testEnd");
        }
    }

    private static void addToQueue(Channel channel, String routingKey, String message) throws IOException {
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
        System.out.println(" [x] Sent '" + message + "'");
    }
}