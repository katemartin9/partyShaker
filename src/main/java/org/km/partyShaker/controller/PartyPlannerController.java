package org.km.partyShaker.controller;
import org.km.partyShaker.party.Party;
import org.km.partyShaker.party.PartyPlanner;
import org.km.partyShaker.repository.Constants;
import org.km.partyShaker.repository.DynamoPartyRepository;
import org.km.partyShaker.repository.FileCocktailRepository;
import org.km.partyShaker.stock.Cocktail;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        model.addAttribute("cocktails", new FileCocktailRepository(Constants.COCKTAILS_FILE).load());
        return "party_planner";
    }

    @PostMapping(value = "/party-planner")
    public String getPartyDetails(Model model, PartyPlanner partyPlanner, HttpServletResponse response) {
        DynamoDbEnhancedClient client = createDynamoDBClient();
        DynamoPartyRepository repositoryParty = new DynamoPartyRepository(client);
        Party party = partyPlanner.createParty();
        System.out.println(party.getCocktailOptions());
        repositoryParty.save(party);
        Cookie cookie = new Cookie("partyCode", party.getPartyCode());
        response.addCookie(cookie);
        return "redirect:/party-page";
    }

    @GetMapping(value = "/party-page")
    public String showParty(Model model) {
        return "party_profile";
    }

}