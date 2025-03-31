package com.skala.repository;

import com.skala.model.Player;
import com.skala.model.PlayerStock;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PlayerRepository {
    private final String PLAYER_FILE = "data/players.txt";
    private ArrayList<Player> playerList = new ArrayList<>();

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

    public void savePlayerList() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PLAYER_FILE))) {
            for (Player player : playerList) {
                writer.write(player.getPlayerId() + "," + player.getPlayerMoney());
                if (player.getPlayerStocks().size() > 0) {
                    writer.write("," + player.getPlayerStocksForFile());
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("파일에 저장하는 중 오류가 발생했습니다.");
        }
    }

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

    public Player findPlayer(String id) {
        for (Player player : playerList) {
            if (player.getPlayerId().equals(id)) {
                return player;
            }
        }
        return null;
    }

    public void addPlayer(Player player) {
        playerList.add(player);
        savePlayerList();
    }

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

    public List<Player> getPlayerList() {
        return this.playerList;
    }
}