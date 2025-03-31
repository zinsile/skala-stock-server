/*
* - 작성자 : 진실
* - 작성일 : 2025.03.30
* - 수정일 : 2025.03.31
* 
* <클래스 개요>
* 플레이어 정보를 파일 시스템에 저장하고 관리하는 리포지토리 클래스
* 
* - 텍스트 파일 형태로 플레이어 데이터 관리
* - PlayerRepositoryHelper 활용
* 
* 1. 파일 입출력: 플레이어 정보 로드 및 저장
* 2. 데이터 변환: 문자열과 객체 간 변환
* 3. 플레이어 관리: 조회, 추가, 삭제 기능
*/

package com.skala.repository;

import com.skala.model.Player;
import com.skala.model.PlayerStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PlayerRepository {
   private final String PLAYER_FILE = "data/players.txt";  // 플레이어 정보 저장 파일 경로
   private ArrayList<Player> playerList = new ArrayList<>();  // 메모리 상의 플레이어 목록
   
   @Autowired
   private PlayerRepositoryHelper helper;

   // 1. 파일 입출력: 파일에서 플레이어 목록 로드
   public void loadPlayerList() {
       playerList.clear();

       try (BufferedReader reader = new BufferedReader(new FileReader(PLAYER_FILE))) {
           String line;
           while ((line = reader.readLine()) != null) {
               Player player = parseLineToPlayer(line);
               if (player != null) {
                   playerList.add(player);
               }
           }
       } catch (IOException e) {
           System.out.println("플레이어 정보가 없습니다.");
       }
   }

   // 1. 파일 입출력: 플레이어 목록을 파일에 저장
   public void savePlayerList() {
       try (BufferedWriter writer = new BufferedWriter(new FileWriter(PLAYER_FILE))) {
           for (Player player : playerList) {
               writer.write(player.getPlayerId() + "," + player.getPlayerMoney());
               if (player.getPlayerStocks().size() > 0) {
                   writer.write("," + helper.convertPlayerStocksToFileFormat(player));
               }
               writer.newLine();
           }
       } catch (IOException e) {
           System.out.println("파일에 저장하는 중 오류가 발생했습니다.");
       }
   }

   // 2. 데이터 변환: 텍스트 라인을 Player 객체로 변환
   private Player parseLineToPlayer(String line) {
       String[] fields = line.split(",");
       if (fields.length > 1) {
           Player player = new Player();
           player.setPlayerId(fields[0]);
           player.setPlayerMoney(Integer.parseInt(fields[1]));

           if (fields.length > 2 && fields[2].indexOf(":") > 0) {
               player.setPlayerStocks(parseFieldToStockList(fields[2]));
           }
           return player;
       } else {
           System.out.println("라인을 분석할 수 없습니다. line=" + line);
           return null;
       }
   }

   // 2. 데이터 변환: 주식 정보 텍스트를 PlayerStock 리스트로 변환
   private ArrayList<PlayerStock> parseFieldToStockList(String field) {
       ArrayList<PlayerStock> list = new ArrayList<>();

       String[] stocks = field.split("\\|");
       for (String stock : stocks) {
           String[] props = stock.split(":");
           if (props.length > 2) {
               list.add(new PlayerStock(props[0], props[1], props[2]));
           }
       }
       return list;
   }

   // 3. 플레이어 관리: ID로 플레이어 조회
   public Player findPlayer(String id) {
       for (Player player : playerList) {
           if (player.getPlayerId().equals(id)) {
               return player;
           }
       }
       return null;
   }

   // 3. 플레이어 관리: 새 플레이어 추가
   public void addPlayer(Player player) {
       playerList.add(player);
       savePlayerList();
   }

   // 3. 플레이어 관리: 플레이어 삭제
   public boolean removePlayer(String id) {
       Player playerToRemove = findPlayer(id);
       if (playerToRemove != null) {
           playerList.remove(playerToRemove);
           savePlayerList();
           return true;
       } else {
           return false;
       }
   }

   // 3. 플레이어 관리: 전체 플레이어 목록 반환
   public List<Player> getPlayerList() {
       return this.playerList;
   }
}