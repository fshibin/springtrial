package com.thefans.kafkamsg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {
	   @Autowired
	   private KafkaTemplate<String, String> kafkaTemplate;

	   public void sendMessage(String msg) {
	      kafkaTemplate.send("learn-spring-boot", msg);
	   }
}
