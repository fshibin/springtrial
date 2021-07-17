package com.thefans.kafkamsg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.thefans.kafkamsg.service.KafkaService;

@RestController
public class SendMessageController {

	@Autowired
	private KafkaService kafkaService;
	
	@GetMapping("/kiaora/{name}")
	public String sendMessage(@PathVariable String name) {
		kafkaService.sendMessage("Kia ora, this is " + name);
		return "Greeting sent to Kafka topic!";
	}
	
}
