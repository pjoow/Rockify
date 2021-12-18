package com.rockstars.rockify.repository;

import com.rockstars.rockify.model.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BandRepository extends JpaRepository<Band, Long> {

    @Query("select id from Band")
    public List<Long> getAllIds();
}
