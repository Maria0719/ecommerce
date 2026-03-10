package co.edu.usbcali.ecommerceusb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "order_items",
        schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_order_product", columnNames = {"order_id", "product_id"})
        },
        indexes = {
                @Index(name = "idx_order_items_order", columnList = "order_id")
        }
)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "order_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_order_items_order"),
            referencedColumnName = "id"
    )
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_order_items_product"),
            referencedColumnName = "id"
    )
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price_snapshot", nullable = false, columnDefinition = "numeric(12,2) check (unit_price_snapshot >= 0)")
    private BigDecimal unitPriceSnapshot;

    @Column(name = "line_total", nullable = false, columnDefinition = "numeric(12,2) check (line_total >= 0)")
    private BigDecimal lineTotal;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT now()")
    private Timestamp createdAt;
}
