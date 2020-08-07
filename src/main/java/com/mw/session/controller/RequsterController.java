package com.mw.session.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RequsterController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("hello")
	public String hello(HttpSession session) {
		session.setAttribute("hello", "eric");
		return "hello eric!";
	}
	
    @GetMapping("/request")
    public String getCookie(HttpSession session) {
        String sessionKey = session.getId();
        session.setAttribute("ID", "p069528");
        logger.info("set userId = " + "p069528" + ", sessionKey = " + sessionKey );
        
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        header.add("Cookie", "SESSION=" + sessionKey);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, header);
        
        ResponseEntity<String> cookieValue = restTemplate.exchange("http://localhost:8090/accepter", HttpMethod.GET, requestEntity, String.class);
        return "requester sessionKey : " + sessionKey + "<br>requester sessionKey : " + cookieValue.getBody();
        // return "requester sessionKey : " + session.getId() + "<br>requester sessionKey : " + cookieValue.getBody();
    }
}
