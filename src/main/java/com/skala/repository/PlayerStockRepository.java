/*
* - 작성자 : 진실
* - 작성일 : 2025.03.31
* 
* <클래스 개요>
* 플레이어의 주식 정보를 관리하는 JPA 리포지토리 인터페이스
* 
* - Spring Data JPA 인터페이스 상속
* - 플레이어의 주식 정보 조회를 위한 메소드 제공
* 
* 1. 특정 플레이어의 주식 조회: 플레이어와 주식 이름으로 조회
* 2. 기본 CRUD 연산: Spring Data JPA에서 자동 제공
*/

package com.skala.repository;

import com.skala.model.Player;
import com.skala.model.PlayerStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerStockRepository extends JpaRepository<PlayerStock, Long> {
    // 1. 특정 플레이어의 주식 조회: 플레이어 ID로 조회
    List<PlayerStock> findByPlayer(Player player);
    
    // 1. 특정 플레이어의 특정 주식 조회: 플레이어와 주식 이름으로 조회
    Optional<PlayerStock> findByPlayerAndStockName(Player player, String stockName);
}