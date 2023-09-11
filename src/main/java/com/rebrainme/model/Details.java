package com.rebrainme.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Details {
    String description;
    Double cost;
}
