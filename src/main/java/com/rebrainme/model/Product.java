package com.rebrainme.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    Long id;
    String name;
    Details details;
}

