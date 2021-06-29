package kr.co.ensmart.frameworkdemo.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@RestController
@RequestMapping("/hello")
public class HelloController {
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@GetMapping("/send-to-topic")
	public void sendToTopic(HelloMessage message) throws Exception {
		simpMessagingTemplate.convertAndSend("/topic/greetings", new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!"));
	}

}
