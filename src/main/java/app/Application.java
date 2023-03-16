package app;

import app.game.Controller;
import app.util.View;
import spark.Session;

import java.util.Map;

import static spark.Spark.*;

public class Application {
    public static void main(String[] args) {
        port(8080);

        staticFileLocation("/public");

        post("/deal", Controller.deal);
        post("/hit", Controller.hit);
        post("/stand", Controller.stand);

        get("/", (req, res) -> {
            Session session = req.session();
            session.attribute("money", 0);

            Map<String, Object> model = new java.util.HashMap<>();
            return View.render(model, "velocity/index.vm");
        });
    }
}
