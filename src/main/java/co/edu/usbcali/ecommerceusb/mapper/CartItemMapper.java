// transforma datos de un formato a otro
package co.edu.usbcali.ecommerceusb.mapper;

import co.edu.usbcali.ecommerceusb.dto.CartItemResponse;
import co.edu.usbcali.ecommerceusb.dto.CreateCartItemRequest;
import co.edu.usbcali.ecommerceusb.model.Cart;
import co.edu.usbcali.ecommerceusb.model.CartItem;
import co.edu.usbcali.ecommerceusb.model.Product;

import java.sql.Timestamp;
import java.util.List;

public class CartItemMapper {

    public static CartItemResponse modelToCartItemResponse(CartItem cartItem) {
        return CartItemResponse.builder()
                .id(cartItem.getId())
                .cartId(cartItem.getCart() != null ? cartItem.getCart().getId() : null)
                .productId(cartItem.getProduct() != null ? cartItem.getProduct().getId() : null)
                .productName(cartItem.getProduct() != null ? cartItem.getProduct().getName() : null)
                .quantity(cartItem.getQuantity())
                .build();
    }

    public static List<CartItemResponse> modelToCartItemResponseList(List<CartItem> cartItems) {
        return cartItems.stream().map(CartItemMapper::modelToCartItemResponse).toList();
    }

    public static CartItem createCartItemRequestToCartItem(CreateCartItemRequest request, Cart cart, Product product) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(request.getQuantity())
                .createdAt(timestamp)
                .updatedAt(timestamp)
                .build();
    }
}
