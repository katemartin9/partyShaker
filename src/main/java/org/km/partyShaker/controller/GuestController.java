package org.km.partyShaker.controller;

import org.km.partyShaker.orders.Guest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class GuestController {
    @GetMapping(value="/guest")
    public String guestForm(Model model) {
        model.addAttribute("guest", new Guest());
        return "guest";
    }

    @PostMapping("/guest")
    public String guestSubmit(@ModelAttribute Guest guest, Model model, HttpServletResponse response) {
        model.addAttribute("guest", guest);
        Cookie cookie = new Cookie("guest", guest.getName());
        response.addCookie(cookie);
        return "redirect:/menu";
    }
}
