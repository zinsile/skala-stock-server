package service;
import java.util.List;

import model.Player;

public interface  PlayerSeviceInterface {

    public void loadPlayer();

    List<Player> getPlayer();

    Player findPlayerByPlayerId(String id);

    void savePlayer();

    boolean removePlayer(String id);

}
