package com.orderService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
		try {
			String orderJson = objectMapper.writeValueAsString(order);
			kafkaTemplate.send(ORDER_CREATED_TOPIC, orderJson);
			logger.info("Published order.created event for order ID: {}", order.getOrderID());
		} catch (JsonProcessingException e) {
			logger.error("Failed to serialize order for Kafka: {}", e.getMessage(), e);
		} catch (Exception e) {
			logger.error("Kafka publishing failed: {}", e.getMessage(), e);
		}
	}
}

