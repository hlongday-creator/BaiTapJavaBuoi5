package com.redbull.webbanhang.service;

import com.redbull.webbanhang.model.CartItem;
import com.redbull.webbanhang.model.Order;
import com.redbull.webbanhang.model.OrderDetail;
import com.redbull.webbanhang.reponsitory.OrderDetailRepository;
import com.redbull.webbanhang.reponsitory.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final OrderDetailRepository orderDetailRepository;
    @Autowired
    private final CartService cartService;

    @Transactional
    public Order createOrder(String customerName, String address, String phone, List<CartItem> cartItems) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setAddress(address);
        order.setPhone(phone);
        order = orderRepository.save(order);

        for (CartItem item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            orderDetailRepository.save(detail);
        }

        // Optionally clear the cart after order placement
        cartService.clearCart();
        return order;
    }
}
