package org.km.partyShaker.controller;
import org.km.partyShaker.party.Party;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PartyPlannerController {

    @GetMapping(value="/party-planner")
    public String planParty(Model model) {
        model.addAttribute("party", new Party());
        return "party_planner";
    }

    @PostMapping(value = "/party-planner")
    public String getPartyDetails(Model model, Party party) {
        System.out.println(party);
        return "redirect:/party-page";
    }

    @GetMapping(value = "/party-page")
    public String showParty(Model model) {
        return "party_profile";
    }

}