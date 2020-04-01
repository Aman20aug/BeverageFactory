package com.tavisca.BeverageFactory.Utils;

import java.util.stream.Stream;

public enum IngredientsPrices {
	Milk(1), Sugar(0.5), Soda(0.5), mint(0.5), water(0.5);

	private final double price;

	IngredientsPrices(double price) {
		this.price = price;
	}
	
	public double getPrice() {
		return price;
	}
	
	public static IngredientsPrices getIngrediant(String ingrediant) {
		
		return Stream.of(IngredientsPrices.values())
				.filter(x -> x.toString().equalsIgnoreCase(ingrediant))
				.findFirst()
				.orElse(null);	
	}

}
