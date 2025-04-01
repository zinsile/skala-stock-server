/*
* - 작성자 : 진실
* - 작성일 : 2025.03.30
* - 수정일 : 2025.03.31
* 
* <클래스 개요>
* 애플리케이션 초기화 및 기본 설정을 담당하는 클래스
* 
* - JPA 사용으로 파일 시스템 관련 코드 제거
* - 필요한 경우 애플리케이션 초기화 로직 포함
* 
* 1. 초기화: 필요한 설정 초기화
*/

package com.skala.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
   
   // JPA를 사용하므로 파일 시스템 기반 초기화 코드는 제거됨
   // 데이터베이스 초기화는 각 서비스 클래스에서 처리
}