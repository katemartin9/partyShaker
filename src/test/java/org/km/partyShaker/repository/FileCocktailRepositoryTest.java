package org.km.partyShaker.repository;
import org.junit.jupiter.api.Test;
import org.km.partyShaker.stock.Cocktail;
import org.km.partyShaker.stock.Ingredient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileCocktailRepositoryTest {
    @Test
    public void testLoad() {
        FileCocktailRepository repository = new FileCocktailRepository();
        List<Cocktail> cocktails = repository.load();
        List<Cocktail> expectedResult = new ArrayList<>();
        expectedResult.add(new Cocktail("Florida Skies", "Tasty",
                Arrays.asList(new Ingredient("rum", 1.0f, true),
                        new Ingredient("soda water", 5.0f, false ))));
        expectedResult.add(new Cocktail("Sex on the Beach", "Sweet and smooth.",
                Arrays.asList(new Ingredient("vodka", .5f, true),
                        new Ingredient("peach schnapps", .5f, true ))));
        assertEquals(expectedResult, cocktails);
    }
}
