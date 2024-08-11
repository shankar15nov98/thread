package com.test.thread.schedule;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.test.thread.controller.ThreadController;
import com.test.thread.service.ThreadService;

@Component
public class Schedular {

	@Autowired
	private ThreadService threadService;

	
	int i=1;
	
	@Scheduled(cron="*/2 * * * * *")
	public void ThreadSchedule()  {
		
	
		new Thread(() -> {
    	
    	final int priority = 10 - i; 
    	try {
			threadService.accessResource("Request-" + priority, priority);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	i++;
    }).start();
    
}
}
