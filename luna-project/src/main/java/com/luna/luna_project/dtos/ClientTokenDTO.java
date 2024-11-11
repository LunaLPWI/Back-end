package com.luna.luna_project.dtos;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ClientTokenDTO {
    private Long id;
    private String name;
    private String email;
    private String token;
}
