package com.skala.controller;

import com.skala.model.Player;
import com.skala.model.PlayerStock;
import com.skala.model.Stock;
import com.skala.service.PlayerService;
import com.skala.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = "*")
public class PlayerController {

    private final PlayerService playerService;
    private final StockService stockService;
    
    @Autowired
    public PlayerController(PlayerService playerService, StockService stockService) {
        this.playerService = playerService;
        this.stockService = stockService;
    }
    
    // Get all players
    @GetMapping
    public List<Player> getAllPlayers() {
        playerService.loadPlayer();
        return playerService.getPlayer();
    }
    
    // Get player by ID
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable String id) {
        playerService.loadPlayer();
        Player player = playerService.findPlayerByPlayerId(id);
        if (player != null) {
            return new ResponseEntity<>(player, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Create new player
    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestParam String id) {
        playerService.loadPlayer();
        if (playerService.findPlayerByPlayerId(id) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT); // Player already exists
        }
        
        Player newPlayer = new Player(id);
        playerService.getPlayer().add(newPlayer);
        playerService.savePlayer();
        return new ResponseEntity<>(newPlayer, HttpStatus.CREATED);
    }
    
    // Remove a player
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removePlayer(@PathVariable String id) {
        boolean removed = playerService.removePlayer(id);
        if (removed) {
            return new ResponseEntity<>("Player removed successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);
        }
    }
    
    // Add money to player
    @PostMapping("/{id}/money")
    public ResponseEntity<String> addMoney(
            @PathVariable String id,
            @RequestParam int amount) {
        
        playerService.loadPlayer();
        Player playerObj = playerService.findPlayerByPlayerId(id);
        
        if (playerObj != null) {
            playerObj.addMoney(amount);
            playerService.savePlayer();
            return new ResponseEntity<>("Money added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);
        }
    }
    
    // Buy stock
    @PostMapping("/{playerId}/buy")
    public ResponseEntity<String> buyStock(
            @PathVariable String playerId,
            @RequestParam String stockName,
            @RequestParam int quantity) {
        
        stockService.loadStock();
        playerService.loadPlayer();
        
        Player playerObj = playerService.findPlayerByPlayerId(playerId);
        if (playerObj == null) {
            return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);
        }
        
        Stock stockObj = stockService.findStockByName(stockName);
        if (stockObj == null) {
            return new ResponseEntity<>("Stock not found", HttpStatus.NOT_FOUND);
        }
        
        int totalCost = stockObj.getStockPrice() * quantity;
        if (totalCost <= playerObj.getPlayerMoney()) {
            playerObj.setPlayerMoney(playerObj.getPlayerMoney() - totalCost);
            playerObj.addStock(new PlayerStock(stockObj, quantity));
            playerService.savePlayer();
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Insufficient funds", HttpStatus.BAD_REQUEST);
        }
    }
    
    // Sell stock
    @PostMapping("/{playerId}/sell")
    public ResponseEntity<String> sellStock(
            @PathVariable String playerId,
            @RequestParam String stockName,
            @RequestParam int quantity) {
        
        stockService.loadStock();
        playerService.loadPlayer();
        
        Player playerObj = playerService.findPlayerByPlayerId(playerId);
        if (playerObj == null) {
            return new ResponseEntity<>("Player not found", HttpStatus.NOT_FOUND);
        }
        
        PlayerStock playerStock = playerObj.findStockByName(stockName);
        if (playerStock == null || playerStock.getStockQuantity() < quantity) {
            return new ResponseEntity<>("Insufficient stock quantity", HttpStatus.BAD_REQUEST);
        }
        
        Stock baseStock = stockService.findStockByName(stockName);
        int earnedMoney = baseStock.getStockPrice() * quantity;
        playerObj.setPlayerMoney(playerObj.getPlayerMoney() + earnedMoney);
        playerStock.setStockQuantity(playerStock.getStockQuantity() - quantity);
        playerObj.updatePlayerStock(playerStock);
        playerService.savePlayer();
        
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    
    // Get player's stocks
    @GetMapping("/{playerId}/stocks")
    public ResponseEntity<List<PlayerStock>> getPlayerStocks(@PathVariable String playerId) {
        playerService.loadPlayer();
        Player player = playerService.findPlayerByPlayerId(playerId);
        
        if (player != null) {
            return new ResponseEntity<>(player.getPlayerStocks(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}