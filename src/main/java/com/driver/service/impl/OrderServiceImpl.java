package com.driver.service.impl;
import com.driver.io.entity.OrderEntity;
import com.driver.io.repository.OrderRepository;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public OrderDto createOrder(OrderDto order) {
        OrderEntity orderEntity= OrderEntity.builder()
                .id(order.getId())
                .orderId(order.getOrderId())
                .cost(order.getCost())
                .items(order.getItems())
                .userId(order.getUserId())
                .status(order.isStatus()).build();
        orderRepository.save(orderEntity);
        return order;
    }

    @Override
    public OrderDto getOrderById(String orderId) throws Exception {
        OrderEntity orderEntity=orderRepository.findByOrderId(orderId);
        if(orderEntity==null){
            throw new Exception(orderId);
        }
        return OrderDto.builder()
                .id(orderEntity.getId())
                .orderId(orderId)
                .cost(orderEntity.getCost())
                .items(orderEntity.getItems())
                .userId(orderEntity.getUserId())
                .status(orderEntity.isStatus()).build();
    }

    @Override
    public OrderDto updateOrderDetails(String orderId, OrderDto order) throws Exception {
        OrderEntity orderEntity=orderRepository.findByOrderId(orderId);
        if(orderEntity==null){
            throw new Exception(orderId);
        }
        OrderEntity orderEntity1= OrderEntity.builder()
                .id(order.getId())
                .orderId(orderId)
                .cost(order.getCost())
                .items(order.getItems())
                .userId(order.getUserId())
                .status(order.isStatus()).build();
        orderRepository.save(orderEntity1);
        return order;


    }

    @Override
    public void deleteOrder(String orderId) throws Exception {
        OrderEntity orderEntity=orderRepository.findByOrderId(orderId);
        if(orderEntity==null){
            throw new Exception(orderId);
        }
        orderRepository.deleteById(orderEntity.getId());
    }

    @Override
    public List<OrderDto> getOrders() {
        List<OrderDto>orderDtos=new ArrayList<>();
        List<OrderEntity>orderEntityList= (List<OrderEntity>) orderRepository.findAll();
        for (OrderEntity orderEntity:orderEntityList){
            OrderDto orderDto=OrderDto.builder()
                    .id(orderEntity.getId())
                    .orderId(orderEntity.getOrderId())
                    .cost(orderEntity.getCost())
                    .items(orderEntity.getItems())
                    .userId(orderEntity.getUserId())
                    .status(orderEntity.isStatus()).build();
            orderDtos.add(orderDto);
        }
        return orderDtos;
    }
}