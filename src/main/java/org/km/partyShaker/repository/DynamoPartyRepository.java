package org.km.partyShaker.repository;

import org.km.partyShaker.orders.Order;
import org.km.partyShaker.party.Party;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;

public class DynamoPartyRepository implements PartyRepository {
    private DynamoDbEnhancedClient client;
    private final String tableName;

    public DynamoPartyRepository(DynamoDbEnhancedClient client) {
        this.client = client;
        this.tableName = "parties";
    }

    @Override
    public void save(Party party) {
        DynamoDbTable<Party> partyDynamoDbTable = client.table(tableName, TableSchema.fromBean(Party.class));
        partyDynamoDbTable.putItem(party);
    }

    @Override
    public Party load(String partyName) {
        DynamoDbTable<Party> partyDynamoDbTable = client.table(tableName, TableSchema.fromBean(Party.class));
        Key key = Key.builder().partitionValue(partyName).build();
        Party loadedParty = partyDynamoDbTable.getItem((GetItemEnhancedRequest.Builder requestBuilder) -> requestBuilder.key(key));
        return loadedParty;
    }
}
