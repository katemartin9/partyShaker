package org.km.partyShaker.repository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.km.partyShaker.party.Party;
import org.km.partyShaker.party.PartyPlanner;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.CreateTableEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.km.partyShaker.DynamoDbLocalTestUtils.createDynamoDBLocalClient;
import static org.km.partyShaker.DynamoDbLocalTestUtils.deleteDynamoDBTable;

public class DynamoPartyRepositoryTest {
    private static DynamoDbEnhancedClient enhancedClient;
    private static DynamoDbClient client;

    private static final String tableName = "parties";

    @BeforeAll
    public static void setUpDynamo() {
        DynamoPartyRepositoryTest.client = createDynamoDBLocalClient();
        DynamoPartyRepositoryTest.enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(client)
                .build();
        createDynamoDBTable();
    }
    @AfterAll
    public static void teardownDynamo() {
        deleteDynamoDBTable(client, tableName);
        client.close();
    }
    public static void createDynamoDBTable() {
        DynamoDbTable<Party> partyDynamoDbTable = enhancedClient.table(tableName, TableSchema.fromBean(Party.class));
        CreateTableEnhancedRequest createTableEnhancedRequest = CreateTableEnhancedRequest.builder()
                .provisionedThroughput(
                        ProvisionedThroughput.builder()
                                .readCapacityUnits(10L)
                                .writeCapacityUnits(10L)
                                .build())
                .build();
        partyDynamoDbTable.createTable(createTableEnhancedRequest);

    }
    @Test
    public void testSave() {
        DynamoPartyRepository repositoryParty = new DynamoPartyRepository(enhancedClient);
        ArrayList<String> cocktails = new ArrayList<>();
        cocktails.add("Florida Skies");
        cocktails.add("Whiskey Sour");
        PartyPlanner partyPlanner = new PartyPlanner();
        partyPlanner.setPartySize(5);
        partyPlanner.setHostName("KM");
        partyPlanner.setPartyName("Wild berries");
        partyPlanner.setPartyStart("2023-02-19 12:00");
        partyPlanner.setPartyEnd("2023-02-20 12:15");
        partyPlanner.setCocktailOptions(cocktails);
        Party party = partyPlanner.createParty();
        String partyCode = party.getPartyCode();
        repositoryParty.save(party);
        Party loadedParty = repositoryParty.load(partyCode);
        assertEquals(5, loadedParty.getPartySize());
        assertEquals("KM", loadedParty.getHostName());
        assertEquals("Wild berries", loadedParty.getPartyName());
        assertEquals("2023-02-19 12:00", loadedParty.getPartyStart());
        assertEquals("2023-02-20 12:15", loadedParty.getPartyEnd());
        assertEquals(cocktails, loadedParty.getCocktailOptions());
    }
}
