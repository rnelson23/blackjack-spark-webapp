package app.game;

import app.util.View;
import spark.*;

import java.util.Map;

public class Controller {
    public static Route deal = (Request request, Response response) -> {
        Session session = request.session();

        Hand dealerHand = new Hand();
        Hand playerHand = new Hand();
        Deck deck = new Deck();

        deck.dealFaceUp(playerHand);
        deck.dealFaceUp(dealerHand);
        deck.dealFaceUp(playerHand);
        deck.dealFaceDown(dealerHand);

        int money = session.attribute("money");
        int bet = Integer.parseInt(request.queryParams("bet"));

        session.attribute("dealerHand", dealerHand);
        session.attribute("playerHand", playerHand);
        session.attribute("deck", deck);
        session.attribute("bet", bet);

        Map<String, Object> model = new java.util.HashMap<>();
        String template = "public/game.vm";

        model.put("dealerHand", dealerHand);
        model.put("playerHand", playerHand);

        if (playerHand.isBlackjack() && !dealerHand.isBlackjack()) {
            money += bet * 1.5;
            template = "public/new.vm";

            model.put("message", "Player has blackjack! Player wins!");
            model.put("money", (money > 0 ? "$" : "-$") + Math.abs(money));

        } else if (!playerHand.isBlackjack() && dealerHand.isBlackjack()) {
            money -= bet;
            template = "public/new.vm";

            model.put("message", "Dealer has blackjack! Dealer wins!");
            model.put("money", (money > 0 ? "$" : "-$") + Math.abs(money));

        } else if (playerHand.isBlackjack() && dealerHand.isBlackjack()) {
            template = "public/new.vm";

            model.put("message", "Both have blackjack! Stand-off!");
            model.put("money", (money > 0 ? "$" : "-$") + Math.abs(money));
        }

        session.attribute("money", money);
        return View.render(model, template);
    };

    public static Route hit = (Request request, Response response) -> {
        Session session = request.session();

        Hand playerHand = session.attribute("playerHand");
        Deck deck = session.attribute("deck");
        deck.dealFaceUp(playerHand);

        int money = session.attribute("money");
        int bet = session.attribute("bet");

        Map<String, Object> model = new java.util.HashMap<>();
        String template = "public/game.vm";

        model.put("dealerHand", session.attribute("dealerHand"));
        model.put("playerHand", playerHand);

        if (playerHand.getValue() > 21) {
            money -= bet;
            template = "public/new.vm";

            model.put("message", "Player busts! Dealer wins!");
            model.put("money", (money > 0 ? "$" : "-$") + Math.abs(money));
        }

        session.attribute("money", money);
        return View.render(model, template);
    };

    public static Route stand = (Request request, Response response) -> {
        Session session = request.session();

        Hand playerHand = session.attribute("playerHand");
        Hand dealerHand = session.attribute("dealerHand");
        Deck deck = session.attribute("deck");

        int money = session.attribute("money");
        int bet = session.attribute("bet");

        dealerHand.flip();

        while (dealerHand.getValue() < 17) {
            deck.dealFaceUp(dealerHand);
        }

        Map<String, Object> model = new java.util.HashMap<>();
        String message;

        model.put("dealerHand", dealerHand);
        model.put("playerHand", playerHand);

        if (dealerHand.getValue() > 21) {
            money += bet;
            message = "Dealer busts! Player wins!";

        } else if (playerHand.getValue() > dealerHand.getValue()) {
            money += bet;
            message = "Player wins!";

        } else if (playerHand.getValue() < dealerHand.getValue()) {
            money -= bet;
            message = "Dealer wins!";

        } else {
            message = "Stand-off!";
        }

        model.put("message", message);
        model.put("money", (money > 0 ? "$" : "-$") + Math.abs(money));

        session.attribute("money", money);
        return View.render(model, "public/new.vm");
    };
}
