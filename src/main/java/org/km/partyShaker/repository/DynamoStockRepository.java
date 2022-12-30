package org.km.partyShaker.repository;
import org.km.partyShaker.stock.Ingredient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;

import java.util.List;

public class DynamoStockRepository implements StockRepository{
    private final DynamoDbEnhancedClient client;
    private final String tableName;

    public DynamoStockRepository(DynamoDbEnhancedClient client) {
        this.client  = client;
        this.tableName = "stock";
    }

    public void save(List<Ingredient> ingredients) {
        DynamoDbTable<Ingredient> stockDynamoDbTable = client.table(tableName, TableSchema.fromBean(Ingredient.class));
        WriteBatch.Builder<Ingredient> builder = WriteBatch.builder(Ingredient.class)
                                        .mappedTableResource(stockDynamoDbTable);
        ingredients.forEach(builder::addPutItem);
        client.batchWriteItem(r -> r.addWriteBatch(builder.build()));
    }
}
