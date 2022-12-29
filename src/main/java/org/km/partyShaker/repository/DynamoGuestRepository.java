package org.km.partyShaker.repository;

import org.km.partyShaker.orders.Guest;
import org.km.partyShaker.orders.Order;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
public class DynamoGuestRepository {
    private DynamoDbEnhancedClient client;
    private final String tableName;

    public DynamoGuestRepository(DynamoDbEnhancedClient client) {
        this.client = client;
        this.tableName = "guests";
    }

    public void save(Guest guest) {
        DynamoDbTable<Guest> guestDynamoDbTable = client.table(tableName, TableSchema.fromBean(Guest.class));
        guestDynamoDbTable.putItem(guest);
    }
}