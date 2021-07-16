package com.thefans.websocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.thefans.websocket.pojo.Greeting;
import com.thefans.websocket.pojo.HelloMessage;

@Controller
public class GreetingController {
   @MessageMapping("/hello")
   @SendTo("/topic/greetings")
   public Greeting greeting(HelloMessage message) throws Exception {
	   System.out.println("Received a message from " + message.getName());
      Thread.sleep(1000); // simulated delay
      return new Greeting("Hello, " + message.getName() + "!");
   }
}