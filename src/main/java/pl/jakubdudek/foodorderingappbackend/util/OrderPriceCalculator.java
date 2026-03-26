package pl.jakubdudek.foodorderingappbackend.util;

import pl.jakubdudek.foodorderingappbackend.model.entity.BasketItem;
import pl.jakubdudek.foodorderingappbackend.model.entity.Order;
import pl.jakubdudek.foodorderingappbackend.model.entity.Variant;

public class OrderPriceCalculator {
    public static float calculateOrderPrice(Order order) {
        float price = 0;

        for(BasketItem item : order.getItems()) {
            Variant variant = item.getProduct().getVariants().get(item.getVariant());
            price += variant.getPrice();
        }

        return price;
    }
}
