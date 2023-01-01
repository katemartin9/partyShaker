package org.km.partyShaker.repository;
import org.km.partyShaker.orders.Guest;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;

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
    public boolean load(Guest guest) {
        DynamoDbTable<Guest> guestDynamoDbTable = client.table(tableName, TableSchema.fromBean(Guest.class));
        Key key = Key.builder().partitionValue(guest.getName()).build();
        Guest loadedGuest = guestDynamoDbTable.getItem((GetItemEnhancedRequest.Builder requestBuilder) -> requestBuilder.key(key));
        return loadedGuest != null;
    }
}
