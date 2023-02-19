package org.km.partyShaker.controller;
import org.km.partyShaker.orders.Order;
import org.km.partyShaker.orders.OrderManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class OrderController {
    OrderManager orderManager = new OrderManager();
    @RequestMapping(value="/ordered", method= RequestMethod.GET)
    public String showOrderPlaced(Model model) {
        return "ordered";
    }

    @RequestMapping(value="/order-queue", method= RequestMethod.GET)
    public String showOrderQueue(Model model, @CookieValue(name = "partyCode") String partyCode) {
        model.addAttribute("queue",  orderManager.getOrderQueue(partyCode));
        return "order_queue";
    }
    @PutMapping(value="/order-queue")
    public ResponseEntity<Order> removeOrder(@RequestBody Order completedOrder) {
        orderManager.updateStatus(completedOrder);
        return ResponseEntity.ok(completedOrder);
    }
}
