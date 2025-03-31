package com.skala.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class AppConfig {
    
    @Value("${file.path.players}")
    private String playersFilePath;
    
    @Value("${file.path.stocks}")
    private String stocksFilePath;
    
    @PostConstruct
    public void init() {
        // Create data directory if it doesn't exist
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        
        // Create players file if it doesn't exist
        File playersFile = new File(playersFilePath);
        if (!playersFile.exists()) {
            try {
                playersFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // Create stocks file if it doesn't exist
        File stocksFile = new File(stocksFilePath);
        if (!stocksFile.exists()) {
            try {
                stocksFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}