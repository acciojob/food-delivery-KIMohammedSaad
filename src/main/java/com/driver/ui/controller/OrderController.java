package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.OrderDetailsResponse;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
	@Autowired
	private OrderService orderService;
	@GetMapping(path="/{id}")
	public OrderDetailsResponse getOrder(@PathVariable String id) throws Exception{
		OrderDto orderDto=orderService.getOrderById(id);
		if(orderDto==null){
			throw new Exception(id);
		}
		OrderDetailsResponse orderDetailsResponse= OrderDetailsResponse.builder()
				.orderId(orderDto.getOrderId())
				.cost(orderDto.getCost())
				.items(orderDto.getItems())
				.status(orderDto.isStatus())
				.userId(orderDto.getUserId())
				.build();
		return orderDetailsResponse;
	}
	@PostMapping()
	public OrderDetailsResponse createOrder(@RequestBody OrderDetailsRequestModel order) {
		OrderDto orderDto=OrderDto.builder()
				.items(order.getItems())
				.cost(order.getCost())
				.userId(order.getUserId())
				.build();
		orderService.createOrder(orderDto);
		OrderDetailsResponse orderDetailsResponse= OrderDetailsResponse.builder()
				.cost(orderDto.getCost())
				.items(orderDto.getItems())
				.userId(orderDto.getUserId())
				.build();
		return orderDetailsResponse;
	}
	@PutMapping(path="/{id}")
	public OrderDetailsResponse updateOrder(@PathVariable String id, @RequestBody OrderDetailsRequestModel order) throws Exception{
		OrderDto orderDto= OrderDto.builder()
				.userId(order.getUserId())
				.cost(order.getCost())
				.items(order.getItems())
				.build();
		try{
			orderService.updateOrderDetails(id,orderDto);
		}catch(Exception e){
			throw new Exception(id);
		}
		return OrderDetailsResponse.builder()
				.orderId(id)
				.userId(order.getUserId())
				.cost(order.getCost())
				.items(order.getItems()).build();
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteOrder(@PathVariable String id) throws Exception {
		try {
			orderService.deleteOrder(id);
		}catch (Exception e){
			throw new Exception(id);
		}
		return new OperationStatusModel("SUCCESS","Delete Order");
	}

	@GetMapping()
	public List<OrderDetailsResponse> getOrders() {
		List<OrderDto>orderDtos=orderService.getOrders();
		List<OrderDetailsResponse>orderDetailsResponses=new ArrayList<>();
		for(OrderDto orderDto:orderDtos){
			OrderDetailsResponse orderDetailsResponse= OrderDetailsResponse.builder()
					.orderId(orderDto.getOrderId())
					.items(orderDto.getItems())
					.cost(orderDto.getCost())
					.userId(orderDto.getUserId())
					.status(orderDto.isStatus()).build();
			orderDetailsResponses.add(orderDetailsResponse);
		}

		return orderDetailsResponses;
	}
}