package com.rockstars.rockify.repository;

import com.rockstars.rockify.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {

}
