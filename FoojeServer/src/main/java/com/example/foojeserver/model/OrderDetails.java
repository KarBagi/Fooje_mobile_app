package com.example.foojeserver.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "orders_details")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDERs_DETAILS_ID")
    private long ordersDetailsId;

    private long ordersId;
    private long productId;

    public long getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(long ordersId) {
        this.ordersId = ordersId;
    }

    public long productId() {
        return productId;
    }

    public void productId(long productId) {
        this.productId = productId;
    }
}
