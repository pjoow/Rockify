package com.rockstars.rockify.controller;

import com.rockstars.rockify.dto.SongDto;
import com.rockstars.rockify.model.Band;
import com.rockstars.rockify.model.Song;
import com.rockstars.rockify.repository.BandRepository;
import com.rockstars.rockify.repository.SongRepository;
import com.rockstars.rockify.util.SongMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/songs")
public class SongController extends BaseController {

    private final SongRepository songRepository;
    private final BandRepository bandRepository;

    SongController(SongRepository songRepository, BandRepository bandRepository) {
        this.songRepository = songRepository;
        this.bandRepository = bandRepository;
    }

    @GetMapping(value = "/all")
    ResponseEntity<List<SongDto>> getAll() {
        return ResponseEntity.ok(songRepository.findAll().stream().map(SongMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "/{songId}")
    ResponseEntity<SongDto> get(@PathVariable Long songId) {
        Optional<Song> song = songRepository.findById(songId);
        return song.map(value -> ResponseEntity.ok(SongMapper.toDto(value))).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    ResponseEntity<HttpStatus> post(@RequestBody SongDto songDto) {
        Long bandId = songDto.getBandId();
        if (bandId == null) {
            return getBadRequest();
        }
        Optional<Band> band = bandRepository.findById(bandId);

        if (band.isPresent()) {
             songRepository.save(SongMapper.toEntity(songDto, band.get()));
            return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
        }
        return getBadRequest();
    }

    @PutMapping
    ResponseEntity<HttpStatus> put(@RequestBody SongDto songDto) {

        if (songDto.getId() == null || songDto.getBandId() == null) {
            return getBadRequest();
        }
        Optional<Song> songOptional = songRepository.findById(songDto.getId());
        Optional<Band> bandOptional = bandRepository.findById(songDto.getBandId());
        if (songOptional.isPresent() && bandOptional.isPresent()) {
            Song song = SongMapper.copy(songOptional.get(), songDto, bandOptional.get());
            songRepository.save(song);

            return getResponseWithStatus(HttpStatus.OK);
        }
        return getBadRequest();
    }

    @DeleteMapping("/{songId}")
    ResponseEntity<HttpStatus> delete(@PathVariable Long songId) {
        if (songRepository.existsById(songId)) {
            songRepository.deleteById(songId);
            return getResponseWithStatus(HttpStatus.OK);
        }
        return getBadRequest();
    }

}
