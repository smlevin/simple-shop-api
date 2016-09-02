package ru.checkout.shop.api.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.server.web.WebServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.checkout.shop.api.exception.OrderNotFoundException;
import ru.checkout.shop.api.model.Order;
import ru.checkout.shop.api.service.ShopOperationService;

import java.util.List;

@RestController
@RequestMapping("shop/api")
public class ShopController {
	private static final Log log = LogFactory.getLog(WebServer.class);

	@Autowired @Qualifier("shopOperationService")
	private ShopOperationService shopOperationService;

	@RequestMapping(value = "order/{uid}", method = RequestMethod.GET, produces = "application/json")
	public Order getOrder(@PathVariable String uid) throws OrderNotFoundException {
		log.info("Call method getOrder for uid " + uid);
		return shopOperationService.getOrderByUid(uid);
	}

	@RequestMapping(value = "create/{userId}", method = RequestMethod.POST,
				produces = "application/json")
	public Order createOrder(@PathVariable long userId, @RequestBody List<Long> products) throws Exception {
		log.info("Call method createOrder for user #" + userId);
		return shopOperationService.createOrder(userId, products);
	}
}
