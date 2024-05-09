package com.dbln9.clients;

import java.util.List;

public record ProductCreateRequest(
        String title,
        String description,
        Float price,
        List<String> imageUrl,
        List<String> category,
        String brand,
        Float rating
) {
}
