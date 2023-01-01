package org.km.partyShaker.controller;
import org.km.partyShaker.orders.Guest;
import org.km.partyShaker.repository.DynamoGuestRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static org.km.partyShaker.repository.Utilities.createDynamoDBClient;

@Controller
public class GuestController {
    @GetMapping(value="/guest")
    public String guestForm(Model model) {
        model.addAttribute("guest", new Guest());
        return "guest";
    }

    @PostMapping("/guest")
    public String guestSubmit(@ModelAttribute Guest guest, Model model, HttpServletResponse response) {
        DynamoDbEnhancedClient client = createDynamoDBClient();
        DynamoGuestRepository repository = new DynamoGuestRepository(client);
        if (!repository.load(guest)) repository.save(guest);
        model.addAttribute("guest", guest);
        Cookie cookie = new Cookie("guest", guest.getName());
        response.addCookie(cookie);
        return "redirect:/menu";
    }
}
