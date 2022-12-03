package org.km.partyShaker.controller;

import org.km.partyShaker.stock.Cocktail;
import org.km.partyShaker.stock.StockManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class MenuController {
    StockManager stockManager = new StockManager("stock.json");

    public MenuController() throws FileNotFoundException, URISyntaxException {
    }

    @GetMapping("/menu")
    public String showMenu() throws FileNotFoundException, URISyntaxException {
        List<Cocktail> availableCocktails;
        try {
            availableCocktails = stockManager.listAvailableCocktails();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return availableCocktails.get(0).getIngredients().toString();
    }
}

