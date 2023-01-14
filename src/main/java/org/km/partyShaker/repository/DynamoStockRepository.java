package org.km.partyShaker.repository;
import org.km.partyShaker.stock.Ingredient;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;

import java.util.ArrayList;
import java.util.List;

public class DynamoStockRepository implements StockRepository{
    private final DynamoDbEnhancedClient client;
    private final String tableName;

    private String partyCode;

    public DynamoStockRepository(DynamoDbEnhancedClient client) {
        this.client  = client;
        this.tableName = "stock";
        this.partyCode = "";
    }

    public void saveMany(List<Ingredient> ingredients) {
        DynamoDbTable<Ingredient> stockDynamoDbTable = client.table(tableName, TableSchema.fromBean(Ingredient.class));
        WriteBatch.Builder<Ingredient> builder = WriteBatch.builder(Ingredient.class)
                                        .mappedTableResource(stockDynamoDbTable);
        ingredients.forEach(builder::addPutItem);
        client.batchWriteItem(r -> r.addWriteBatch(builder.build()));
    }
    public void save(Ingredient ingredient) {
        //ingredient.withPartyCode(this.partyCode);
        System.out.println(ingredient);
        DynamoDbTable<Ingredient> stockDynamoDbTable = client.table(tableName, TableSchema.fromBean(Ingredient.class));
        stockDynamoDbTable.putItem(ingredient);
    }
    public List<Ingredient> getStock(String partyCode) {

        DynamoDbIndex<Ingredient> stockDynamoDbIndex = client.table(tableName, TableSchema.fromBean(Ingredient.class))
                .index("party-name-index");
        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(partyCode)
                        .build());
        Iterable<Page<Ingredient>> stock = stockDynamoDbIndex.query(
                QueryEnhancedRequest.builder().queryConditional(queryConditional).build());
        List<Ingredient> currentStock = new ArrayList<>();
        stock.forEach(it -> currentStock.addAll(it.items()));
        return currentStock;
    }

    public DynamoStockRepository withPartyCode(String partyCode) {
        this.partyCode = partyCode;
        return this;
    }
}