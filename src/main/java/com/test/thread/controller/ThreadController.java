package com.test.thread.controller;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.thread.service.ThreadService;

@RestController
@RequestMapping("/resource")
public class ThreadController {

	@Autowired
    private ThreadService threadService;

    @PostMapping("/access")
    public void accessResource(@RequestParam String requestId, @RequestParam int priority) throws FileNotFoundException {
    	threadService.accessResource(requestId, priority);
    }
    
}
