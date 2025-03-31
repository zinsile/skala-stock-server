package com.skala.service;

import com.skala.model.Stock;
import java.util.List;

public interface StockServiceInterface {
    void loadStock();
    
    List<Stock> getStock();
    
    String getStockListForMenu();
    
    Stock findStockByIndex(int index);
    
    Stock findStockByName(String name);
    
    void addStock(String name, int price);
}