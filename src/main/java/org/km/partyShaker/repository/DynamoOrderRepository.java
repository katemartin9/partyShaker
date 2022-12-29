package org.km.partyShaker.repository;
import org.km.partyShaker.orders.Order;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
public class DynamoOrderRepository implements OrderRepository {
    private final DynamoDbEnhancedClient client;
    private final String tableName;

    public DynamoOrderRepository(DynamoDbEnhancedClient client) {
        this.client = client;
        this.tableName = "orders";
    }
    public void save(Order order) {
        DynamoDbTable<Order> orderDynamoDbTable = client.table(tableName, TableSchema.fromBean(Order.class));
        orderDynamoDbTable.putItem(order);
    }
    public void loadByGuestName(String guestName) {

    }
}
