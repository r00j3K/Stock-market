package com.github.r00j3k.simplified_stock_market.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RestController
@RequestMapping("/chaos")
public class ChaosController {
    @PostMapping
    public void killInstance(){
        log.info("Chaos endpoint called. Killing instance...");
        
        new Thread(() -> {
            try {
                Thread.sleep(500); //wait 0.5s for server to send 200 OK response before shutting down
                System.exit(1);    
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
