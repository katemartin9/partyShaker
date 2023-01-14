package org.km.partyShaker.controller;
import org.km.partyShaker.orders.Guest;
import org.km.partyShaker.orders.Order;
import org.km.partyShaker.orders.OrderManager;
import org.km.partyShaker.stock.Cocktail;
import org.km.partyShaker.stock.StockManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import org.springframework.ui.Model;

@Controller
public class MenuController {

    StockManager stockManager = new StockManager();
    OrderManager orderManager = new OrderManager();
    public MenuController()  {

    }
    @RequestMapping(value="/menu", method= RequestMethod.GET)
    public String showMenu(Model model,  @CookieValue(name = "guest") String guestName) {
        String partyCode = guestName.split("#")[1];
        stockManager.setPartyCode(partyCode);
        model.addAttribute("menu", stockManager.listAvailableCocktails());
        return "menu";
    }
    @RequestMapping(value="/menu/{cocktailName}", method= RequestMethod.GET)
    public String placeOrder(Model model, @PathVariable String cocktailName, @CookieValue(name = "guest") String guestName) {
        String partyCode = guestName.split("#")[1];
        stockManager.setPartyCode(partyCode);
        if (!cocktailName.isEmpty()) {
            Optional<Cocktail> cocktail = stockManager.getCocktailByName(cocktailName);
            if (!cocktail.isPresent()) {
                return "menu";
            }
            if (stockManager.removeStock(cocktail.get(), partyCode)) {
                Order order = new Order(guestName, 1, cocktail.get());
                orderManager.addToQueue(order);
                return "redirect:/ordered";
            }
        }
        return "menu";
    }

}

