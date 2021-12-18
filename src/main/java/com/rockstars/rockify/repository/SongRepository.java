package com.rockstars.rockify.repository;

import com.rockstars.rockify.model.Band;
import com.rockstars.rockify.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    @Query("select id from Song")
    public List<Long> getAllIds();
}
