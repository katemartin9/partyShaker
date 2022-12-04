package org.km.partyShaker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

@Controller
public class OrderController {

    @RequestMapping(value="/ordered", method= RequestMethod.GET)
    public String showOrderPlaced(Model model) {

        return "ordered";
    }
}
