package org.km.partyShaker.controller;

import org.km.partyShaker.orders.Guest;
import org.km.partyShaker.orders.Order;
import org.km.partyShaker.orders.OrderManager;
import org.km.partyShaker.stock.Cocktail;
import org.km.partyShaker.stock.StockManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MenuController {
    Guest guest = new Guest();
    StockManager stockManager = new StockManager("stock.json");
    OrderManager orderManager = new OrderManager();
    public MenuController() throws FileNotFoundException, URISyntaxException {
        guest.createName("km");
    }
    @RequestMapping(value="/menu", method= RequestMethod.GET)
    public String showMenu(Model model) throws FileNotFoundException, URISyntaxException {
        model.addAttribute("menu", stockManager.listAvailableCocktails());
        return "menu";
    }
    @RequestMapping(value="/menu/{cocktailName}", method= RequestMethod.GET)
    public String placeOrder(Model model, @PathVariable String cocktailName) throws FileNotFoundException, URISyntaxException {
        model.addAttribute("menu", stockManager.listAvailableCocktails());
        System.out.println(cocktailName);
        if (!cocktailName.isEmpty()) {
            Optional<Cocktail> cocktail = stockManager.getCocktailByName(cocktailName);
            if (!cocktail.isPresent()) {
                return "menu";
            }
            Order order = new Order(guest, 1, cocktail.get());
            orderManager.addToQueue(order);
            return "redirect:/ordered";
        }
        return "menu";
    }

}

