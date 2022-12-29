package org.km.partyShaker.repository;
import org.km.partyShaker.orders.Order;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DynamoOrderRepository {
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

    public Order latestOrderByGuestName(String guestName) {
        DynamoDbTable<Order> orderDynamoDbTable = client.table(tableName, TableSchema.fromBean(Order.class));
        PageIterable<Order> orders = orderDynamoDbTable.query(
                QueryEnhancedRequest.builder().queryConditional(QueryConditional.keyEqualTo(k -> k.partitionValue(guestName))
                ).scanIndexForward(false).limit(1).build()
        );
        return orders.items().stream().findFirst().orElse(null);
    }
    public List<Order> allOrdersByGuestName(String guestName) {
        DynamoDbTable<Order> orderDynamoDbTable = client.table(tableName, TableSchema.fromBean(Order.class));
        PageIterable<Order> orders = orderDynamoDbTable.query(QueryConditional.keyEqualTo(k -> k.partitionValue(guestName)));
        List<Order> orderItems = new ArrayList<>();
        orders.items().forEach(orderItems::add);
        return orderItems;
    }

}
