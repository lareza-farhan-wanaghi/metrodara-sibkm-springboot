package com.metrodata.productservice.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tb_m_product")
@Data // Hash, to String, Getter dan Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long id;

    @Column(name = "nama", nullable = false, length = 50)
    private String name;

    private long price;

    private long quantity;

}
