package com.tavisca.BeverageFactory.Utils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.annotations.Nullable;

public enum Beverages {

	COFFEE("Coffee", "Coffee", "milk, sugar, water", 5), CHAI("Chai", "Tea", " milk, sugar, water", 4), BANANASMOOTHI(
			"Banana Smoothie", "banana", "milk, sugar, water", 6), STRAWBERRYSHAKE("Strawberry Shake", "Strawberries",
					"sugar, milk, water", 7), MOJITO("Mojito", "Lemon", "sugar, water, soda, mint", 7.5);

	private final double price;
	private final String ingreadiant;
	private final String name;
	private final String keyIngrediants;

	Beverages(String name, String keyIngrediant, String ingreadiant, double price) {
		this.price = price;
		this.ingreadiant = ingreadiant;
		this.name = name;
		this.keyIngrediants = keyIngrediant;
	}

	public double getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}

	public String getKeyIngrediants() {
		return keyIngrediants;
	}

	public static List<?> getIngrediants(Beverages beverage,boolean keyIngrediant) {
		String[] ingredients = null;
		if (keyIngrediant) {
			ingredients = beverage.keyIngrediants.split(",");
			return Stream.of(ingredients).map(x -> x.toLowerCase().trim()).collect(Collectors.toList());
		}
		ingredients = beverage.ingreadiant.split(",");
		return Stream.of(ingredients).map(x -> IngredientsPrices.getIngrediant(x.trim())).collect(Collectors.toList());
	}

	public static Beverages getBeverage(String beverage) {
		return Stream.of(Beverages.values()).filter(x -> x.name.equalsIgnoreCase(beverage)).findFirst()
				.orElseThrow(() -> new RuntimeException("Beverage is not in the Menu list"));
	}

}
