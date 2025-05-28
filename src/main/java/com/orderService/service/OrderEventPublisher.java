package com.orderService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderService.exception.KafkaPublishException;
import com.orderService.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventPublisher {

	private static final Logger logger = LoggerFactory.getLogger(OrderEventPublisher.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	private static final String ORDER_CREATED_TOPIC = "order.created";

	public void publishOrderCreatedEvent(Order order) {
		String orderJson = serializeOrder(order);  // Can throw KafkaPublishException
		sendMessage(orderJson);                    // Can throw KafkaPublishException
		logger.info("Published order.created event for order ID: {}", order.getOrderID());
	}

	private String serializeOrder(Order order) {
		try {
			return objectMapper.writeValueAsString(order);
		} catch (JsonProcessingException e) {
			throw new KafkaPublishException("Failed to serialize order to JSON for Kafka", e);
		}
	}

	private void sendMessage(String orderJson) {
		try {
			kafkaTemplate.send(ORDER_CREATED_TOPIC, orderJson);
		} catch (Exception e) {
			throw new KafkaPublishException("Failed to send order.created event to Kafka", e);
		}
	}
}