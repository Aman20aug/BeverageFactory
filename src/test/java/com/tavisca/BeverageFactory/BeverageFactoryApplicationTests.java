package com.tavisca.BeverageFactory;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tavisca.BeverageFactory.Controller.BeverageFactoryController;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class BeverageFactoryApplicationTests {

	@Autowired
	BeverageFactoryController beverageFactoryControllerTest;

	@Test
	public void whenUserProvide_SingleOrderSuccesfully() throws InterruptedException, ExecutionException {
		// Given
		String[] order = { "coffee" };
		// when
		String amount = beverageFactoryControllerTest.getFinalAmount(order);
		// then
		assertEquals("5.0", amount);
	}

	@Test
	public void whenUserProvide_SingleOrderRemovingOneIngrediant() throws InterruptedException, ExecutionException {
		// Given
		String[] order = { "coffee,-milk" };
		// when
		String amount = beverageFactoryControllerTest.getFinalAmount(order);
		// then
		assertEquals("4.0", amount);
	}

	@Test
	public void whenUserProvide_SingleOrderRemovingAllIngrediant() throws InterruptedException, ExecutionException {
		// Given
		String[] order = { "coffee,-milk,-sugar,-water" };
		// when
		String amount = beverageFactoryControllerTest.getFinalAmount(order);
		// then
		assertEquals("You have to re-order because because Cannot exclude all the Ingrediant", amount);
	}

	@Test
	public void whenUserProvide_MultipleOrderSuccesfully() throws InterruptedException, ExecutionException {
		// Given
		String[] order = { "coffee", "Chai" };
		// when
		String amount = beverageFactoryControllerTest.getFinalAmount(order);
		// then
		assertEquals("9.0", amount);
	}

	@Test
	public void whenUserProvide_MultipleOrderRemovingIngrediant() throws InterruptedException, ExecutionException {
		// Given
		String[] order = { "coffee", "Chai,-milk" };
		// when
		String amount = beverageFactoryControllerTest.getFinalAmount(order);
		// then
		assertEquals("8.0", amount);
	}

	@Test
	public void whenUserProvide_MultipleOrderRemovingKeyIngrediant() throws InterruptedException, ExecutionException {
		// Given
		String[] order = { "coffee", "Chai,-Tea" };
		// when
		String amount = beverageFactoryControllerTest.getFinalAmount(order);
		// then
		assertEquals("You have to re-order because you cannot Remove Key Ingrediants : Tea", amount);
	}

	@Test
	public void whenUserProvide_MultipleOrderRemovingInvalidIngrediant() throws InterruptedException, ExecutionException {
		// Given
		String[] order = { "coffee", "Chai,-abc" };
		// when
		String amount = beverageFactoryControllerTest.getFinalAmount(order);
		// then
		assertEquals("You have to re-order because selected ingrediant is not in a Ingrediants List : abc", amount);
	}

	@Test
	public void whenUserProvide_InvalidOrderNotInTheMenuList() throws InterruptedException, ExecutionException {
		// Given
		String[] order = { "coffee", "ABC" };
		// when
		String amount = beverageFactoryControllerTest.getFinalAmount(order);
		// then
		assertEquals("Beverage is not in the Menu list", amount);
	}

}
