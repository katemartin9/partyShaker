package org.km.partyShaker.controller;
import org.km.partyShaker.orders.OrderManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OrderController {
    OrderManager orderManager = new OrderManager();
    @RequestMapping(value="/ordered", method= RequestMethod.GET)
    public String showOrderPlaced(Model model) {
        return "ordered";
    }

    @RequestMapping(value="/order-queue", method= RequestMethod.GET)
    public String showOrderQueue(Model model) {
        model.addAttribute("queue",  orderManager.getOrderQueue());
        return "order_queue";
    }
}
