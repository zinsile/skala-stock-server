/*
* - 작성자 : 진실
* - 작성일 : 2025.03.31
* 
* <클래스 개요>
* 애플리케이션 시작 시 기본 데이터를 초기화하는 클래스
* 
* - 애플리케이션 시작 시 필요한 기본 데이터 생성
* - 테스트를 위한 샘플 데이터 추가 기능 제공
* 
* 1. 초기화: 기본 주식 데이터 생성
* 2. 샘플 데이터: 필요한 경우 샘플 플레이어 생성
*/

package com.skala.config;

import com.skala.model.Player;
import com.skala.model.Stock;
import com.skala.repository.PlayerRepository;
import com.skala.repository.StockRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DataInitializer {

    private final StockRepository stockRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public DataInitializer(StockRepository stockRepository, PlayerRepository playerRepository) {
        this.stockRepository = stockRepository;
        this.playerRepository = playerRepository;
    }

    // 애플리케이션 시작 시 실행
    @PostConstruct
    public void initialize() {
        // 기본 주식 데이터 초기화
        initializeStocks();
        
        // 개발 환경에서만 샘플 플레이어 생성
        // 실제 환경에서는 조건부 실행 (예: @Profile("dev"))
        // initializeSamplePlayers();
    }

    // 기본 주식 데이터 초기화
    private void initializeStocks() {
        // 주식 데이터가 없을 경우에만 초기화
        if (stockRepository.count() == 0) {
            stockRepository.save(new Stock("TechCorp", 100));
            stockRepository.save(new Stock("GreenEnergy", 80));
            stockRepository.save(new Stock("HealthPlus", 120));
            stockRepository.save(new Stock("FinanceGroup", 150));
            stockRepository.save(new Stock("RetailChain", 70));
        }
    }

    // 샘플 플레이어 초기화 (개발 환경용)
    @Profile("dev")
    private void initializeSamplePlayers() {
        // 플레이어 데이터가 없을 경우에만 초기화
        if (playerRepository.count() == 0) {
            Player samplePlayer = new Player("sample");
            playerRepository.save(samplePlayer);
        }
    }
}