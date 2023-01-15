package org.km.partyShaker.repository;
import org.km.partyShaker.orders.Guest;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.ArrayList;
import java.util.List;


public class DynamoGuestRepository implements GuestRepository {
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
    public int getRegisteredGuests(String partyCode) {
        DynamoDbIndex<Guest> orderDynamoDbIndex = client.table(tableName, TableSchema.fromBean(Guest.class))
                .index("party-guestName-index");
        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(partyCode)
                        .build());
        Iterable<Page<Guest>> guests = orderDynamoDbIndex.query(
                QueryEnhancedRequest.builder().queryConditional(queryConditional).scanIndexForward(false).build()
        );
        List<Guest> allGuests = new ArrayList<>();
        guests.forEach(it -> allGuests.addAll(it.items()));
        return allGuests.size();
    }
}
