package org.km.partyShaker.repository;
import org.km.partyShaker.orders.Order;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import java.util.ArrayList;
import java.util.List;

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
    public List<Order> allPendingOrders(String partyCode) {
        DynamoDbIndex<Order> orderDynamoDbIndex = client.table(tableName, TableSchema.fromBean(Order.class))
                .index("statusParty-timestamp-index");
        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue("0#" + partyCode)
             .build());
        Iterable<Page<Order>> orders = orderDynamoDbIndex.query(
                QueryEnhancedRequest.builder().queryConditional(queryConditional).scanIndexForward(false).build()
        );
        List<Order> pendingOrders = new ArrayList<>();
        orders.forEach(it -> pendingOrders.addAll(it.items()));
        return pendingOrders;
    }

}
