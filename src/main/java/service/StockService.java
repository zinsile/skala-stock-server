package service;
import java.util.List;

import model.Stock;
import repository.StockRepository;

public class StockService implements StockServiceInterface {
    private final StockRepository stockRepository = new StockRepository();

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
