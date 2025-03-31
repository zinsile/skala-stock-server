package com.skala.service;

import com.skala.model.Stock;
import com.skala.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService implements StockServiceInterface {
    
    private final StockRepository stockRepository;
    
    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void loadStock() {
        stockRepository.loadStockList();
    }

    @Override
    public List<Stock> getStock() {
        return stockRepository.getStockList();
    }

    @Override
    public String getStockListForMenu() {
        return stockRepository.getStockListForMenu();
    }

    @Override
    public Stock findStockByIndex(int index) {
        return stockRepository.findStock(index);
    }

    @Override
    public Stock findStockByName(String name) {
        return stockRepository.findStock(name);
    }

    @Override
    public void addStock(String name, int price) {
        stockRepository.addStock(name, price);
    }
}