package com.luna.luna_project.dtos.establishment;

import lombok.Data;

import java.util.List;


@Data
public class FavoriteEstablishmentsDTO {
    private Long clientId;
    private List<Long> establishmentIds;
}
