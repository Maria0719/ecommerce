package co.edu.usbcali.ecommerceusb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "inventory_movements",
        schema = "public",
        indexes = {
                @Index(
                        name = "idx_inventory_movements_product_created_at",
                        columnList = "product_id, created_at DESC"
                ),
                @Index(name = "idx_inventory_movements_order", columnList = "order_id")
        }
)
public class InventoryMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_inv_mov_product"),
            referencedColumnName = "id"
    )
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "order_id",
            foreignKey = @ForeignKey(name = "fk_inv_mov_order"),
            referencedColumnName = "id"
    )
    private Order order;

    @Column(nullable = false)
    private String type;

    @Column(name = "qty", nullable = false)
    private Integer qty;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT now()")
    private Timestamp createdAt;
}
