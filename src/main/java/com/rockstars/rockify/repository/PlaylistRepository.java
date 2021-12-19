package com.rockstars.rockify.repository;

import com.rockstars.rockify.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

}
