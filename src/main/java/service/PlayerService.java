package service;
import repository.PlayerRepository;

import java.util.List;

import model.Player;

public class PlayerService implements PlayerSeviceInterface {
    private final PlayerRepository playerRepository = new PlayerRepository();

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
