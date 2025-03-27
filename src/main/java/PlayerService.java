import repository.PlayerRepository;
import model.Player;

public class PlayerService {
    private final PlayerRepository playerRepository = new PlayerRepository();

    @Override
    public void loadPlayer() {
        playerRepository.loadPlayerList();
    }

    @Override
    public Player findPlayerByPlayerId(String id) {
        return playerRepository.findPlayer(id);
    }

    @Override
    public void savePlayer() {
        playerRepository.savePlayerList();
    }


}
