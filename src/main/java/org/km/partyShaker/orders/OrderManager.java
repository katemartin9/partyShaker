package org.km.partyShaker.orders;
import org.km.partyShaker.repository.DynamoOrderRepository;
import org.km.partyShaker.stock.Cocktail;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import java.util.List;
import static org.km.partyShaker.repository.Utilities.createDynamoDBClient;

public class OrderManager {
    DynamoOrderRepository repository;

    public OrderManager() {
        DynamoDbEnhancedClient client = createDynamoDBClient();
        this.repository = new DynamoOrderRepository(client);
    }
    public void addToQueue(String guestName, Cocktail cocktail) {
        if(!checkIfAlreadyInQueue(guestName)) {
            repository.save(new Order(guestName, generateNextOrderId(guestName), cocktail));
        }
    }
    public void updateStatus(Order completedOrder) {
        //TODO: cocktail name is null so investigate html
        System.out.println(completedOrder);
        repository.update(completedOrder);
    }
    public boolean checkIfAlreadyInQueue(String guestName) {
        Order latestOrder = repository.latestOrderByGuestName(guestName);
        if (latestOrder != null) {
            return latestOrder.getStatus() == 0;
        } return false;
    }
    public int generateNextOrderId(String guestName) {
        Order latestOrder = repository.latestOrderByGuestName(guestName);
        return latestOrder.getOrderId() + 1;
    }

    public List<Order> getOrderQueue(String partyCode) {
        return repository.allPendingOrders(partyCode);
    }

}
