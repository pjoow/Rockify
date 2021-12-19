package com.rockstars.rockify.controller;

import com.rockstars.rockify.dto.BandDto;
import com.rockstars.rockify.model.Band;
import com.rockstars.rockify.repository.BandRepository;
import com.rockstars.rockify.repository.SongRepository;
import com.rockstars.rockify.util.BandMapper;
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
@RequestMapping("/bands")
public class BandController extends BaseController {

    private final SongRepository songRepository;
    private final BandRepository bandRepository;

    BandController(SongRepository songRepository, BandRepository bandRepository) {
        this.songRepository = songRepository;
        this.bandRepository = bandRepository;
    }

    @GetMapping(value = "/all")
    ResponseEntity<List<BandDto>> getAll() {
        return ResponseEntity.ok(bandRepository.findAll().stream().map(BandMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "/{bandId}")
    ResponseEntity<BandDto> get(@PathVariable Long bandId) {
        Optional<Band> band = bandRepository.findById(bandId);
        return band.map(value -> ResponseEntity.ok(BandMapper.toDto(value))).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    ResponseEntity<HttpStatus> post(@RequestBody BandDto bandDto) {
        if(bandRepository.existsByName(bandDto.getName()))
        {
            return getBadRequest();
        }
        bandRepository.save(BandMapper.toEntity(bandDto));
        return getResponseWithStatus(HttpStatus.OK);
    }

    @PutMapping
    ResponseEntity<HttpStatus> put(@RequestBody BandDto bandDto) {

        Optional<Band> bandOptional = bandRepository.findById(bandDto.getId());
        if(bandOptional.isEmpty() || bandRepository.existsByName(bandDto.getName()))
        {
            return getBadRequest();
        }

        Band band = bandOptional.get();
        band.setName(bandDto.getName());
        bandRepository.save(band);
        return getResponseWithStatus(HttpStatus.OK);
    }

    @DeleteMapping("/{bandId}")
    ResponseEntity<HttpStatus> delete(@PathVariable Long bandId) {
        if (bandRepository.existsById(bandId)) {
            bandRepository.deleteById(bandId);
            return getResponseWithStatus(HttpStatus.OK);
        }
        return getBadRequest();
    }
}
