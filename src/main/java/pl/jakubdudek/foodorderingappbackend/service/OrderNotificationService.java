package pl.jakubdudek.foodorderingappbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import pl.jakubdudek.foodorderingappbackend.model.dto.response.OrderDto;

@Component
@RequiredArgsConstructor
public class OrderNotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    public void notifyRestaurantStaffNew(OrderDto order) {
        System.out.println(order.restaurantId());
        messagingTemplate.convertAndSend("/topic/restaurant/"+order.restaurantId()+"/orders/new", order);
    }

    public void notifyRestaurantStaff(OrderDto order) {
        System.out.println(order.restaurantId());
        messagingTemplate.convertAndSend("/topic/restaurant/"+order.restaurantId()+"/orders", order);
    }

    public void notifyCustomer(OrderDto order) {
        messagingTemplate.convertAndSend("/topic/order/"+order.token(), order);
    }
}