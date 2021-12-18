package com.rockstars.rockify.controller;

import com.rockstars.rockify.dto.SongDto;
import com.rockstars.rockify.model.Song;
import com.rockstars.rockify.repository.SongRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SongController {

    private final SongRepository songRepository;

    SongController(SongRepository songRepository)
    {
        this.songRepository = songRepository;
    }

    @GetMapping
    List<SongDto> getAll()
    {
        return songRepository.findAll();
    }
}
