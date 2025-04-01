/*
* - 작성자 : 진실
* - 작성일 : 2025.03.30
* - 수정일 : 2025.03.31
* 
* <클래스 개요>
* 주식 정보를 관리하는 JPA 리포지토리 인터페이스
* 
* - 파일 기반 저장소에서 JPA 기반 저장소로 변경
* - Spring Data JPA 인터페이스 상속
* 
* 1. 주식 조회: 이름으로 주식 조회
* 2. 기본 CRUD 연산: Spring Data JPA에서 자동 제공
*/

package com.skala.repository;

import com.skala.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
    // 1. 주식 조회: 이름으로 주식 조회 (기본 제공되는 findById로 대체 가능)
    Optional<Stock> findByStockName(String stockName);
}