import java.util.List;
import model.Stock;
import repository.StockRepository;

public class StockService {
    private final StockRepository stockRepository = new StockRepository();

    @Override
    public void loadStock() {
        stockRepository.loadStockList();
    }

    @Override
    public String getStockListForMenu() {
        stockRepository.getStockListForMenu();
    }

    @Override
    public Stock findStockByIndex(int index) {
        stockRepository.findStock(index);
    }









}
