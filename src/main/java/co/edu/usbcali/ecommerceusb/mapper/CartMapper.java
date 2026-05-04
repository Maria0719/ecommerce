package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.CartResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCartRequest;
import co.edu.usbcali.ecommerceusb.model.Cart;
import co.edu.usbcali.ecommerceusb.model.User;

import java.sql.Timestamp;
import java.util.List;

public class CartMapper {

    public static CartResponse modelToCartResponse(Cart cart) {
        return CartResponse.builder()
                .id(cart.getId())
                .userId(cart.getUser() != null ? cart.getUser().getId() : null)
                .userFullName(cart.getUser() != null ? cart.getUser().getFullName() : null)
                .status(cart.getStatus())
                .build();
    }

    public static List<CartResponse> modelToCartResponseList(List<Cart> carts) {
        return carts.stream().map(CartMapper::modelToCartResponse).toList();
    }

    public static Cart createCartRequestToCart(CreateCartRequest request, User user) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return Cart.builder()
                .user(user)
                .status(request.getStatus())
                .createdAt(timestamp)
                .updatedAt(timestamp)
                .build();
    }
}
