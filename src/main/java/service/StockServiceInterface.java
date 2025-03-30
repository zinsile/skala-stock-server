package service;
import java.util.List;

import model.Stock;

public interface StockServiceInterface {
    void loadStock();

    List<Stock> getStock();

    String getStockListForMenu();

    Stock findStockByIndex(int index);

    Stock findStockByName(String name);

    void addStock(String name, int price);
}
