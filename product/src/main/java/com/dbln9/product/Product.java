package com.dbln9.product;

import com.dbln9.clients.ProductAbstract;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product extends ProductAbstract {
    @Id
    @GeneratedValue(generator = "product_id_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName = "product_id_sequence", name = "product_id_sequence")
    private Long id;
    private String title;
    private String description;
    private Float price;
    @ElementCollection
    @CollectionTable(name = "images")
    private List<String> imageUrl;
    @ElementCollection
    @CollectionTable(name = "categories")
    private List<String> category;
    private String brand;
    private Float rating;


}
