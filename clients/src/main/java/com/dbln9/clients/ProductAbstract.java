package com.dbln9.clients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductAbstract {
    private Long id;
    private String title;
    private String description;
    private Float price;
    private List<String> imageUrl;
    private List<String> category;
    private String brand;
    private Float rating;
}
