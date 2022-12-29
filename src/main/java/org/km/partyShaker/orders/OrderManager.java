package org.km.partyShaker.orders;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.km.partyShaker.PartyShakerApplication;
import org.km.partyShaker.stock.Cocktail;
import org.km.partyShaker.stock.Ingredient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class OrderManager {
    Deque<Order> orderQueue;

    public OrderManager() {
        this.orderQueue = new ArrayDeque<>();
    }

    public void addToQueue(Order newOrder) {orderQueue.addLast(newOrder);
    }
    public void removeFromQueue(Order completedOrder) {
        orderQueue.remove(completedOrder);
    }

    public boolean checkIfAlreadyInQueue(Guest guest) {
        return true;
    }

    public Order[] getOrderQueue() throws FileNotFoundException, URISyntaxException {
        URL resource = PartyShakerApplication.class.getClassLoader().getResource("orders.json");
        File file = Paths.get(resource.toURI()).toFile();
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(file));
        return gson.fromJson(reader, Order[].class);
    }
}
