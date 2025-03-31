/*
* - 작성자 : 진실
* - 작성일 : 2025.03.30
* - 수정일 : 2025.03.31
* 
* <클래스 개요>
* 플레이어 저장소 관련 헬퍼 기능을 제공하는 클래스
* 
* - 저장소와 서비스 간 순환 참조 문제 해결
* - 플레이어 데이터 변환 기능 제공
* 
* 1. 데이터 변환: 플레이어 주식 정보를 파일 저장 형식으로 변환
*/

package com.skala.repository;

import com.skala.model.Player;
import org.springframework.stereotype.Component;

@Component
public class PlayerRepositoryHelper {
   
   // 1. 데이터 변환: 플레이어 주식 정보를 파일 저장용 문자열로 변환
   public String convertPlayerStocksToFileFormat(Player player) {
       StringBuilder sb = new StringBuilder();
       for (int i = 0; i < player.getPlayerStocks().size(); i++) {
           if (i > 0) {
               sb.append("|");
           }
           sb.append(player.getPlayerStocks().get(i));
       }
       return sb.toString();
   }
}