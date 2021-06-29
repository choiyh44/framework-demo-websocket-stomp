package kr.co.ensmart.frameworkdemo.app.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class WebsocketSendToUserController {
	@Autowired
    private ObjectMapper objectMapper;

    @MessageMapping("/message")
    @SendToUser("/queue/reply")
    public Greeting processMessageFromClient(HelloMessage message, Principal principal) throws Exception {
        return new Greeting("Hello, " + message.getName());
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }
}
