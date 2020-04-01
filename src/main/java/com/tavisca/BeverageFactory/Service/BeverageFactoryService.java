package com.tavisca.BeverageFactory.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;
import com.tavisca.BeverageFactory.Utils.Beverages;
import com.tavisca.BeverageFactory.Utils.IngredientsPrices;

@Service
public class BeverageFactoryService {

	public double getFinalAmount(String order) throws InterruptedException, ExecutionException {
		String[] demand = order.split(",");
		Beverages beverage = Beverages.getBeverage(demand[0]);
		double price = beverage.getPrice();
		double deductedPrice = 0;
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		List<IngredientsPrices> ingrediant = null;

		if (demand.length == 1) {
			return beverage.getPrice();
		} else {
			Future<List<IngredientsPrices>> ingrediantFuture = executorService.submit(() -> {
				return (List<IngredientsPrices>) Beverages.getIngrediants(beverage, false);

			});
			for (int i = 1; i < demand.length; i++) {
				String ingrediantValue = demand[i].trim();
				if (ingrediantValue.charAt(0) == '-') {
					ingrediantValue = ingrediantValue.substring(1);
					IngredientsPrices ingrediantexclusion = IngredientsPrices.getIngrediant(ingrediantValue);
					if (null == ingrediantexclusion) {
						if (Beverages.getIngrediants(beverage, true).contains(ingrediantValue.toLowerCase())) {
							throw new RuntimeException(
									"You have to re-order because you cannot Remove Key Ingrediants : "
											+ ingrediantValue);
						} else {
							throw new RuntimeException(
									"You have to re-order because selected ingrediant is not in a Ingrediants List : "
											+ ingrediantValue);
						}

					} else {
						ingrediant = ingrediantFuture.get();
						if (ingrediant.remove(ingrediantexclusion)) {
							deductedPrice += ingrediantexclusion.getPrice();
						}
					}
				}
			}
			if (ingrediant.size() == 0) {
				throw new RuntimeException("You have to re-order because because Cannot exclude all the Ingrediant");
			}
			return price - deductedPrice;
		}

	}

}
