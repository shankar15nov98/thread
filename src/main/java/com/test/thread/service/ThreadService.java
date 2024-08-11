package com.test.thread.service;

import java.util.concurrent.ScheduledExecutorService;
import java.io.*; 
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.concurrent.locks.Lock; //
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
@Service
public class ThreadService {

	    private static final long DELAY = 2000L; 

	    private final PriorityBlockingQueue<RequestAccess> requestQueue = new PriorityBlockingQueue<>();
	    private final AtomicLong lastAccessedTime = new AtomicLong(0L);

	    public void accessResource(String requestId, int priority) throws FileNotFoundException {
	    	RequestAccess request = new RequestAccess(requestId, priority);

	        synchronized (this) {
	            requestQueue.add(request);
	            queueProcessing();
	        }
	    }

	    public void queueProcessing() throws FileNotFoundException {
	        while (!requestQueue.isEmpty()) {
	        	RequestAccess currentRequest = requestQueue.poll();
	            long now = System.currentTimeMillis();
	            long elapsedTime = now - lastAccessedTime.get();

	            if (elapsedTime < DELAY) {
	                try {
	                    Thread.sleep(DELAY - elapsedTime);
	                } catch (InterruptedException e) {
	                    Thread.currentThread().interrupt();
	                }
	            }
	            
					
					try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\VENKATESH C\\Documents\\testfile.txt"))) {
						String str;    
						while((str=br.readLine())!=null){  
						System.out.println("File content: "+str);  
						}
					} catch (FileNotFoundException e) {
						throw e;
					} catch (IOException e) {
						e.printStackTrace();
					}  

				 
	            
	            System.out.println("Processing request: " + currentRequest.getRequestId());
	            lastAccessedTime.set(System.currentTimeMillis());
	        }
	    }

	    class RequestAccess implements Comparable<RequestAccess> {
	        private final String requestId;
	        private final int priority;

	        public RequestAccess(String requestId, int priority) {
	            this.requestId = requestId;
	            this.priority = priority;
	        }

	        public String getRequestId() {
	            return requestId;
	        }

	        @Override
	        public int compareTo(RequestAccess o) {
	            return Integer.compare(o.priority, this.priority); // Higher priority first
	        }
	    }

}
