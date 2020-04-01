package com.tavisca.BeverageFactory.Controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.tavisca.BeverageFactory.Service.BeverageFactoryService;

@RestController
public class BeverageFactoryController {

	@Autowired
	BeverageFactoryService beverageFactoryService;

	@PostMapping(value = "Order")
	public String getFinalAmount(@RequestBody String[] orders ) throws InterruptedException, ExecutionException {
	try {
		double amount=0;
		if (null != orders) {
			for (int i = 0; i < orders.length; i++) {
				amount = amount + beverageFactoryService.getFinalAmount(orders[i]);
			
			}
		}
		return new Double(amount).toString();
	}catch(RuntimeException ex) {
		return ex.getMessage();
	}
	
	}

}
