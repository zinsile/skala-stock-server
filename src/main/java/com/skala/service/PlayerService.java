package com.skala.service;

import com.skala.model.Player;
import com.skala.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService implements PlayerServiceInterface {
    
    private final PlayerRepository playerRepository;
    
    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void loadPlayer() {
        playerRepository.loadPlayerList();
    }

    @Override
    public List<Player> getPlayer() {
        return playerRepository.getPlayerList();
    }

    @Override
    public Player findPlayerByPlayerId(String id) {
        return playerRepository.findPlayer(id);
    }

    @Override
    public void savePlayer() {
        playerRepository.savePlayerList();
    }

    @Override
    public boolean removePlayer(String id) {
        return playerRepository.removePlayer(id);
    }
}