import com.google.gson.Gson;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.port;
import static spark.Spark.post;

public class WebServer {
    static Gson gson = new Gson();
    static SkalaStockMarket market = new SkalaStockMarket();

    public static void main(String[] args) {
        port(8080);
        cors();

        get("/api/stocks", (req, res) -> {
            res.type("application/json");
            return gson.toJson(market.getStocks());
        });

        get("/api/players", (req, res) -> {
            res.type("application/json");
            return gson.toJson(market.getPlayers());
        });

        post("/api/buy", (req, res) -> {
            String player = req.queryParams("player");
            String stock = req.queryParams("stock");
            int quantity = Integer.parseInt(req.queryParams("quantity"));
            market.buyStock(player, stock, quantity);
            return "success";
        });

        post("/api/sell", (req, res) -> {
            String player = req.queryParams("player");
            String stock = req.queryParams("stock");
            int quantity = Integer.parseInt(req.queryParams("quantity"));
            market.sellStock(player, stock, quantity);
            return "success";
        });

        // [추가] 플레이어 탈퇴 API
        post("/api/removePlayer", (req, res) -> {
            String playerId = req.queryParams("player");
            boolean removed = market.removePlayer(playerId);
            if (removed) {
                return "Player removed successfully.";
            } else {
                res.status(404);
                return "Player not found.";
            }
        });

        // 주식 종목 추가
        post("/api/addStock", (req, res) -> {
            String name = req.queryParams("name");
            int price = Integer.parseInt(req.queryParams("price"));
            market.addStock(name, price);  // ← stockRepository 통해 저장
            return "success";
        });
    }

    private static void cors() {
        options("/*", (req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET,POST");
            res.header("Access-Control-Allow-Headers", "*");
            return "OK";
        });
        before((req, res) -> res.header("Access-Control-Allow-Origin", "*"));
    }
}
