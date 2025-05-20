package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.establishment.FavoriteEstablishmentsDTO;
import com.luna.luna_project.models.Client;
import com.luna.luna_project.models.Establishment;
import com.luna.luna_project.models.Favorite;
import com.luna.luna_project.models.FavoriteId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FavoriteMapper {

    // Entidade -> DTO
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "establishment.id", target = "establishmentIds")
    default FavoriteEstablishmentsDTO favoriteToFavoriteDTO(List<Favorite> favorites) {
        if (favorites == null || favorites.isEmpty()) {
            return null;
        }

        FavoriteEstablishmentsDTO dto = new FavoriteEstablishmentsDTO();
        dto.setClientId(favorites.get(0).getClient().getId());
        List<Long> establishmentIds = favorites.stream()
                .map(fav -> fav.getEstablishment().getId())
                .collect(Collectors.toList());
        dto.setEstablishmentIds(establishmentIds);
        return dto;
    }

    default Favorite favoriteDTOtoFavorite(Client client, Establishment establishment) {
        Favorite favorite = new Favorite();
        favorite.setId(new FavoriteId(client.getId(), establishment.getId()));
        favorite.setClient(client);
        favorite.setEstablishment(establishment);
        return favorite;
    }

}
