package com.skala.service;

import com.skala.model.Player;
import java.util.List;

public interface PlayerServiceInterface {
    void loadPlayer();
    
    List<Player> getPlayer();
    
    Player findPlayerByPlayerId(String id);
    
    void savePlayer();
    
    boolean removePlayer(String id);
}