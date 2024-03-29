package org.km.partyShaker.controller;
import org.km.partyShaker.party.Party;
import org.km.partyShaker.party.PartyPlanner;
import org.km.partyShaker.repository.Constants;
import org.km.partyShaker.repository.DynamoPartyRepository;
import org.km.partyShaker.repository.DynamoStockRepository;
import org.km.partyShaker.repository.FileCocktailRepository;
import org.km.partyShaker.stock.Cocktail;
import org.km.partyShaker.stock.Ingredient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.km.partyShaker.repository.Utilities.createDynamoDBClient;

@Controller
public class PartyPlannerController {

    @GetMapping(value="/party-planner")
    public String planParty(Model model) {
        model.addAttribute("party", new Party());
        model.addAttribute("cocktails", new FileCocktailRepository().load());
        return "party_planner";
    }

    @PostMapping(value = "/party-planner")
    public String getPartyDetails(Model model, PartyPlanner partyPlanner, HttpServletResponse response) {
        DynamoDbEnhancedClient client = createDynamoDBClient();
        DynamoPartyRepository repositoryParty = new DynamoPartyRepository(client);
        Party party = partyPlanner.createParty();
        //TODO: check this recognises timezone
        repositoryParty.save(party);
        Cookie cookie = new Cookie("partyCode", party.getPartyCode());
        response.addCookie(cookie);
        return "redirect:/party-page";
    }

    @GetMapping(value = "/party-page")
    public String showParty(Model model, @CookieValue(name = "partyCode") String partyCode) {
        DynamoDbEnhancedClient client = createDynamoDBClient();
        DynamoPartyRepository repositoryParty = new DynamoPartyRepository(client);
        DynamoStockRepository repositoryStock = new DynamoStockRepository(client);
        Party party = repositoryParty.load(partyCode);
        model.addAttribute("party", party);
        int partySize = party.getPartySize();
        //TODO: add this to Stock Manager (creating stock for the first time and ability to edit)
        List<Ingredient> clonedIngredients = party.cloneIngredients();
        clonedIngredients
                .forEach(
                ingredient -> {
                    float quantity = ingredient.getQuantity()*25*partySize;
                    ingredient.setQuantity(quantity);
                    ingredient.setParty(partyCode);
                });
        repositoryStock.saveMany(clonedIngredients);
        return "party_profile";
    }

}