package com.giuliabalaban.portfolio.service;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ContactRateLimiter {

private static final int MAX_REQUESTS=5;
private static final Duration WINDOW=Duration.ofMinutes(10);

private final Map<String,Deque<Instant>> requests=new ConcurrentHashMap<>();

public synchronized boolean isAllowed(String ip){

Instant now=Instant.now();
Instant limit=now.minus(WINDOW);

Deque<Instant> attempts=requests.computeIfAbsent(
ip,
key->new ArrayDeque<>()
);

while(!attempts.isEmpty()&&attempts.peekFirst().isBefore(limit)){
attempts.removeFirst();
}

if(attempts.size()>=MAX_REQUESTS){
return false;
}

attempts.addLast(now);

return true;
}

}