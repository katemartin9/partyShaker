package org.km.partyShaker.repository;
import org.junit.jupiter.api.Test;
import org.km.partyShaker.orders.Order;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.CreateTableEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.EnhancedGlobalSecondaryIndex;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ProjectionType;
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.km.partyShaker.DynamoDbLocalTestUtils.createDynamoDBLocalClient;
import static org.km.partyShaker.DynamoDbLocalTestUtils.deleteDynamoDBTable;

public class DynamoOrderRepositoryTest {
    private static DynamoDbEnhancedClient enhancedClient;
    private static DynamoDbClient client;

    private static final String tableName = "orders";

    @BeforeAll
    public static void setUpDynamo() {
        DynamoOrderRepositoryTest.client = createDynamoDBLocalClient();
        DynamoOrderRepositoryTest.enhancedClient = DynamoDbEnhancedClient.builder()
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
        DynamoDbTable<Order> orderDynamoDbTable = enhancedClient.table(tableName, TableSchema.fromBean(Order.class));
        CreateTableEnhancedRequest createTableEnhancedRequest = CreateTableEnhancedRequest.builder()
                .provisionedThroughput(
                        ProvisionedThroughput.builder()
                                .readCapacityUnits(10L)
                                .writeCapacityUnits(10L)
                                .build())
                .globalSecondaryIndices(
                        EnhancedGlobalSecondaryIndex.builder()
                                .indexName("statusParty-timestamp-index")
                                .projection(p -> p.projectionType(ProjectionType.ALL))
                                .provisionedThroughput(ProvisionedThroughput.builder()
                                        .readCapacityUnits(10L)
                                        .writeCapacityUnits(10L)
                                        .build())
                                .build())
                .build();
        orderDynamoDbTable.createTable(createTableEnhancedRequest);
    }
    @Test
    public void testSave() {
        Order order = new Order("Guppy#KX90", 1, "Whiskey Sour");
        DynamoOrderRepository repositoryOrder = new DynamoOrderRepository(enhancedClient);
        repositoryOrder.save(order);
        Order loadedOrder = repositoryOrder.latestOrderByGuestName("Guppy#KX90");
        assertEquals(1, loadedOrder.getOrderId());
        assertEquals("Guppy#KX90", loadedOrder.getGuestName());
        assertEquals(0, loadedOrder.getStatus());
        assertEquals("0#KX90", loadedOrder.getStatusParty());
    }
    @Test
    public void testLatestOrderByGuestName() {
        Order orderFirst = new Order("Guppy#KX90", 1, "Whiskey Sour");
        Order orderSecond = new Order("Guppy#KX90", 2, "Florida Skies");
        DynamoOrderRepository repositoryOrder = new DynamoOrderRepository(enhancedClient);
        repositoryOrder.save(orderFirst);
        repositoryOrder.save(orderSecond);
        Order loadedOrder = repositoryOrder.latestOrderByGuestName("Guppy#KX90");
        assertEquals(2, loadedOrder.getOrderId());
        assertEquals("Guppy#KX90", loadedOrder.getGuestName());
        assertEquals(0, loadedOrder.getStatus());
        assertEquals("0#KX90", loadedOrder.getStatusParty());
        assertEquals("Florida Skies", loadedOrder.getCocktail());
    }
    @Test
    public void testAllOrdersByGuestName() {

    }
    @Test
    public void testAllPendingOrders() throws InterruptedException {
        DynamoOrderRepository repositoryOrder = new DynamoOrderRepository(enhancedClient);
        Order orderFirst = new Order("Guppy#KX90", 1, "Whiskey Sour");
        orderFirst.setStatus(1);
        repositoryOrder.save(orderFirst);
        Order orderSecond = new Order("Mojo#KX90", 1, "Florida Skies");
        repositoryOrder.save(orderSecond);
        Order orderThird = new Order("Nan#KX90", 1, "Florida Skies");
        repositoryOrder.save(orderThird);
        List<Order> uncompletedOrders = repositoryOrder.allPendingOrders("KX90");
        assertThat(uncompletedOrders.get(0)).usingRecursiveComparison()
                .isEqualTo(orderSecond);
        assertThat(uncompletedOrders.get(1)).usingRecursiveComparison()
                .isEqualTo(orderThird);
    }

}
