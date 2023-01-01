package org.km.partyShaker.repository;
import org.km.partyShaker.stock.Ingredient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository {
    public void saveMany(List<Ingredient> ingredients);
    public void save(Ingredient ingredient);
    public List<Ingredient> getStock();
}
