package com.luna.luna_project.repositories;

import com.luna.luna_project.models.Client;
import com.luna.luna_project.models.Favorite;
import com.luna.luna_project.models.FavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {
    void deleteAllByClient(Client client);
}
