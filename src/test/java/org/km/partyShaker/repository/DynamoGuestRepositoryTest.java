package org.km.partyShaker.repository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.km.partyShaker.orders.Guest;
import org.km.partyShaker.orders.GuestManager;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.CreateTableEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.EnhancedGlobalSecondaryIndex;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ProjectionType;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.km.partyShaker.DynamoDbLocalTestUtils.*;

public class DynamoGuestRepositoryTest {
    private static DynamoDbEnhancedClient enhancedClient;
    private static DynamoDbClient  client;

    private static final String tableName = "guests";

    @BeforeAll
    public static void setUpDynamo() {
        DynamoGuestRepositoryTest.client = createDynamoDBLocalClient();
        DynamoGuestRepositoryTest.enhancedClient = DynamoDbEnhancedClient.builder()
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
        DynamoDbTable<Guest> guestDynamoDbTable = enhancedClient.table(tableName, TableSchema.fromBean(Guest.class));
        CreateTableEnhancedRequest createTableEnhancedRequest = CreateTableEnhancedRequest.builder()
                .provisionedThroughput(
                        ProvisionedThroughput.builder()
                                .readCapacityUnits(10L)
                                .writeCapacityUnits(10L)
                                .build())
                .globalSecondaryIndices(
                        EnhancedGlobalSecondaryIndex.builder()
                                .indexName("party-guestName-index")
                                .projection(p -> p.projectionType(ProjectionType.ALL))
                                .provisionedThroughput(ProvisionedThroughput.builder()
                                        .readCapacityUnits(10L)
                                        .writeCapacityUnits(10L)
                                        .build())
                                .build())
                .build();
        guestDynamoDbTable.createTable(createTableEnhancedRequest);

    }
    @Test
    public void testSave() {
        DynamoGuestRepository repositoryGuest = new DynamoGuestRepository(enhancedClient);
        GuestManager guestManager = new GuestManager();
        guestManager.setName("Momo");
        guestManager.setActivationCode("KX90");
        Guest newGuest = guestManager.createGuest();
        repositoryGuest.save(newGuest);
        Guest loadedGuest = repositoryGuest.loadGuest(newGuest);
        assertEquals("Momo#KX90", newGuest.getName());
        assertEquals("Momo#KX90", loadedGuest.getName());
    }

    @Test
    public void testGetRegisteredGuests() {
        DynamoGuestRepository repositoryGuest = new DynamoGuestRepository(enhancedClient);
        String partyCode = "LN8";

        GuestManager guestManager = new GuestManager();
        guestManager.setName("Momo");
        guestManager.setActivationCode(partyCode);
        Guest guestOne = guestManager.createGuest();
        repositoryGuest.save(guestOne);
        guestManager.setName("Lala");
        guestManager.setActivationCode(partyCode);
        Guest guestTwo = guestManager.createGuest();
        repositoryGuest.save(guestTwo);
        assertEquals(2, repositoryGuest.getRegisteredGuests(partyCode));
    }

}
