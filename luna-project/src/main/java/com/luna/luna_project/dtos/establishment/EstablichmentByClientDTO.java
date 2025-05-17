package com.luna.luna_project.dtos.establishment;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstablichmentByClientDTO {
    private Long id;
    private Boolean favorite;
}
