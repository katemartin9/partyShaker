package org.km.partyShaker.orders;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.km.partyShaker.PartyShakerApplication;
import org.km.partyShaker.repository.DynamoGuestRepository;
import org.km.partyShaker.repository.DynamoOrderRepository;
import org.km.partyShaker.stock.Cocktail;
import org.km.partyShaker.stock.Ingredient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;

import static org.km.partyShaker.repository.Utilities.createDynamoDBClient;

public class OrderManager {
    DynamoOrderRepository repository;

    public OrderManager() {
        DynamoDbEnhancedClient client = createDynamoDBClient();
        this.repository = new DynamoOrderRepository(client);
    }

    public void addToQueue(Order newOrder) {
        repository.save(newOrder);
    }
    public void removeFromQueue(Order completedOrder) {
        completedOrder.setStatus(1);
        repository.save(completedOrder);
    }
    public boolean checkIfAlreadyInQueue(Guest guest) {
        Order latestOrder = repository.latestOrderByGuestName(guest.getName());
        return latestOrder.getStatus() == 0;
    }

    public List<Order> getOrderQueue() {
        return repository.allPendingOrders();
    }
}
