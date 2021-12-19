package com.rockstars.rockify.repository;

import com.rockstars.rockify.model.Band;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BandRepository extends JpaRepository<Band, Long> {

    boolean existsByName(String name);

}
