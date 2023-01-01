package org.km.partyShaker.orders;
import org.km.partyShaker.repository.DynamoOrderRepository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import java.util.List;
import static org.km.partyShaker.repository.Utilities.createDynamoDBClient;

public class OrderManager {
    DynamoOrderRepository repository;

    public OrderManager() {
        DynamoDbEnhancedClient client = createDynamoDBClient();
        this.repository = new DynamoOrderRepository(client);
    }

    public void addToQueue(Order newOrder) {
        if(!checkIfAlreadyInQueue(newOrder.guestName)) {
            repository.save(newOrder);
        }
    }
    public void removeFromQueue(Order completedOrder) {
        completedOrder.setStatus(1);
        repository.save(completedOrder);
    }
    public boolean checkIfAlreadyInQueue(String guestName) {
        Order latestOrder = repository.latestOrderByGuestName(guestName);
        if (latestOrder != null) {
            return latestOrder.getStatus() == 0;
        } return false;
    }

    public List<Order> getOrderQueue() {
        return repository.allPendingOrders();
    }
}
