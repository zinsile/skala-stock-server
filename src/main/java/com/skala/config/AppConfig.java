/*
* - 작성자 : 진실
* - 작성일 : 2025.03.30
* - 수정일 : 2025.03.31
* 
* <클래스 개요>
* 애플리케이션 초기화 및 기본 설정을 담당하는 클래스
* 
* - 필요한 디렉토리와 파일 구조 생성
* - 프로퍼티 파일의 설정값 활용
* 
* 1. 초기화: 애플리케이션 구동 시 필요한 설정 초기화
* 2. 파일 관리: 필요한 데이터 파일 및 디렉토리 생성
*/

package com.skala.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class AppConfig {
   
   @Value("${file.path.players}")
   private String playersFilePath;    // 플레이어 파일 경로 프로퍼티
   
   @Value("${file.path.stocks}")
   private String stocksFilePath;     // 주식 파일 경로 프로퍼티
   
   // 1. 초기화: 애플리케이션 시작 시 실행되는 초기화 메소드
   @PostConstruct
   public void init() {
       // 2. 파일 관리: 데이터 디렉토리 생성
       File dataDir = new File("data");
       if (!dataDir.exists()) {
           dataDir.mkdirs();
       }
       
       // 2. 파일 관리: 플레이어 파일 생성
       File playersFile = new File(playersFilePath);
       if (!playersFile.exists()) {
           try {
               playersFile.createNewFile();
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
       
       // 2. 파일 관리: 주식 파일 생성
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