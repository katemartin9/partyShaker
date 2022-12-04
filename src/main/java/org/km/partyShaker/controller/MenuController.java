package org.km.partyShaker.controller;

import org.km.partyShaker.stock.Cocktail;
import org.km.partyShaker.stock.StockManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MenuController {
    StockManager stockManager = new StockManager("stock.json");

    public MenuController() throws FileNotFoundException, URISyntaxException {
    }

    @RequestMapping(value="/menu", method= RequestMethod.GET)
    public String showMenu(Model model) throws FileNotFoundException, URISyntaxException {
        model.addAttribute("menu", stockManager.listAvailableCocktails());
        return "menu";
    }
}

