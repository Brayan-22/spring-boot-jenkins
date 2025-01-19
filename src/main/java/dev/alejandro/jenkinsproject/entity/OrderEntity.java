package dev.alejandro.jenkinsproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity(name = "\"Order\"")
@Table(name = "\"order\"")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "\"order_id\"")
    private Integer id;
    @Column(name = "\"product_name\"")
    private String productName;
    @Column(name = "\"order_price\"")
    private double price;
    @Column(name = "\"order_quantity\"")
    private Integer quantity;
}
