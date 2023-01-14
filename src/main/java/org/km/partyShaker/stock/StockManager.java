package org.km.partyShaker.stock;
import org.km.partyShaker.repository.Constants;
import org.km.partyShaker.repository.DynamoStockRepository;
import org.km.partyShaker.repository.FileCocktailRepository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.km.partyShaker.repository.Utilities.createDynamoDBClient;

public class StockManager {
    DynamoStockRepository repository;
    String partyCode;
    public StockManager() {
        DynamoDbEnhancedClient client = createDynamoDBClient();
        this.repository = new DynamoStockRepository(client);
    }
    public String getPartyCode() {
        return partyCode;
    }
    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }
    public List<Ingredient> getStock() {
        return repository.getStock(this.partyCode);
    }
    public Result checkIfCanMakeCocktail(Cocktail cocktail) {
        List<Ingredient> stock = getStock();
        List<Ingredient> cocktailIngredients = cocktail.getIngredients();
        for (Ingredient cocktailIngredient: cocktailIngredients) {
            if (!stock.contains(cocktailIngredient))
                return new Result(false, stock);
            int stockIndex = stock.indexOf(cocktailIngredient);
            Ingredient stockIngredient = stock.get(stockIndex);
            if (stockIngredient.getQuantity() < cocktailIngredient.getQuantity() * 25)
                return new Result(false, stock);
        }
        return new Result(true, stock);
    }
    public void addStock(List<Ingredient> ingredients) {
        repository.saveMany(ingredients);
        }
    public void addStock(Ingredient ingredient) {
        repository.save(ingredient);
    }
    public boolean removeStock(Cocktail cocktail, String partyCode) {
        Result result = this.checkIfCanMakeCocktail(cocktail);
        if (!result.ableToMakeCocktail)
            return false;
        for (int idx = 0; idx < cocktail.getIngredients().size(); idx ++) {
            Ingredient ingredient = cocktail.ingredients.get(idx);
            int stockIndex = result.currentStock.indexOf(ingredient);
            Ingredient stockIngredient = result.currentStock.get(stockIndex);
            System.out.println("Stock ingredient before: " + stockIngredient);
            stockIngredient.quantity -= ingredient.quantity * 25;
            System.out.println("Stock ingredient updated: " + stockIngredient);
            this.addStock(stockIngredient);
        }
        return true;
    }
    public List<Cocktail> listAvailableCocktails() {
        FileCocktailRepository cocktail_repo = new FileCocktailRepository(Constants.COCKTAILS_FILE);
        List<Cocktail> cocktails = cocktail_repo.load();
        List<Cocktail> availableCocktails = new ArrayList<>();
        for (Cocktail cocktail : cocktails) {
            System.out.println(cocktail);
            Result result = this.checkIfCanMakeCocktail(cocktail);
            System.out.println(result.ableToMakeCocktail);
            if (result.ableToMakeCocktail) {
                availableCocktails.add(cocktail);
            }
        }
        return availableCocktails;
    }
    public Optional<Cocktail> getCocktailByName(String name) {
        List<Cocktail> availableCocktails;
        try {
            availableCocktails = this.listAvailableCocktails();
        } catch (Exception e) {return Optional.empty();};
    return availableCocktails.stream().filter(cocktail -> cocktail.name.equals(name)).findFirst();
    }

    private class Result {

        public Boolean ableToMakeCocktail;
        public List<Ingredient> currentStock;
        Result(boolean ableToMakeCocktail, List<Ingredient> currentStock) {
            this.ableToMakeCocktail = ableToMakeCocktail;
            this.currentStock = currentStock;
        }
    }
}