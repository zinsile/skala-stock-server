package repository;
/*
 * <작성자>
 * 진실
 * 
 * <최종 업데이트 날짜>
 * 2025.03.27
 * 
 * <클래스 개요>
 * 플레이어(Player) 정보를 파일에서 불러오고 저장하며, 메모리에서 관리하는 클래스
 * 
 * 1. loadPlayerList() : 파일에서 플레이어 정보 읽기
 * 2. savePlayerList() : 플레이어 정보를 파일로 저장
 * 3. parseLineToPlayer(String line) : 파일 라인을 Player 객체로 변환
 * 4. parseFieldToStockList(String field) : 주식 문자열을 PlayerStock 리스트로 변환
 * 5. findPlayer(String id) : 플레이어 ID로 검색
 * 6. addPlayer(Player player) : 플레이어 추가 후 파일에 저장
 * 7. removePlayer(String id) : 플레이어 삭제 후 파일에 반영
 * 8. getPlayerList() : 플레이어 목록 반환
 */

 import java.io.BufferedReader;
 import java.io.BufferedWriter;
 import java.io.FileReader;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.List;

import model.Player;
import model.PlayerStock;
 
 public class PlayerRepository {
 
     // 플레이어 정보를 저장할 파일
     private final String PLAYER_FILE = "data/players.txt";
 
     // 플레이어 정보 목록 (메모리)
     private ArrayList<Player> playerList = new ArrayList<>();
 
     // 1. 파일에서 플레이어 정보 읽기
     public void loadPlayerList() {
         playerList.clear(); // 기존 플레이어 목록 초기화
 
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
 
     // 2. 플레이어 정보를 파일로 저장
     public void savePlayerList() {
         try (BufferedWriter writer = new BufferedWriter(new FileWriter(PLAYER_FILE))) {
             for (Player player : playerList) {
                 writer.write(player.getplayerId() + "," + player.getPlayerMoney());
                 if (player.getPlayerStocks().size() > 0) {
                     writer.write("," + player.getPlayerStocksForFile());
                 }
                 writer.newLine();
             }
         } catch (IOException e) {
             System.out.println("파일에 저장하는 중 오류가 발생했습니다.");
         }
     }
 
     // 3. 파일 라인을 Player 객체로 변환
     private Player parseLineToPlayer(String line) {
         String fileds[] = line.split(",");
         if (fileds.length > 1) {
             Player player = new Player();
             player.setplayerId(fileds[0]);
             player.setPlayerMoney(Integer.parseInt(fileds[1]));
 
             if (fileds.length > 2 && fileds[2].indexOf(":") > 0) {
                 player.setPlayerStocks(parseFieldToStockList(fileds[2]));
             }
             return player;
         } else {
             System.out.println("라인을 분석할 수 없습니다. line=" + line);
             return null;
         }
     }
 
     // 4. 주식 문자열을 PlayerStock 리스트로 변환 
     private ArrayList<PlayerStock> parseFieldToStockList(String field) {
         ArrayList<PlayerStock> list = new ArrayList<>();
 
         String stocks[] = field.split("\\|");
         for (int i = 0; i < stocks.length; i++) {
             String props[] = stocks[i].split(":");
             if (props.length > 1) {
                 list.add(new PlayerStock(props[0], props[1], props[2]));
             }
         }
         return list;
     }
 
     // 5. 플레이어 ID로 검색
     public Player findPlayer(String id) {
         for (Player player : playerList) {
             if (player.getplayerId().equals(id)) {
                 return player;
             }
         }
         return null;
     }
 
     // 6. 플레이어 추가 후 파일에 저장
     public void addPlayer(Player player) {
         playerList.add(player);
         savePlayerList();
     }
 
     // 7. 플레이어 탈퇴 (삭제) 후 파일에 저장
     public boolean removePlayer(String id) {
         Player playerToRemove = findPlayer(id);
         if (playerToRemove != null) {
             playerList.remove(playerToRemove);
             savePlayerList();
             return true; // 삭제 성공
         } else {
             return false; // 해당 ID 없음
         }
     }
 
     // 8. 플레이어 목록 반환
     public List<Player> getPlayerList() {
         return this.playerList;
     }
 }
 