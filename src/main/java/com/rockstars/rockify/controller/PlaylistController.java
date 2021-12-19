package com.rockstars.rockify.controller;

import com.rockstars.rockify.dto.PlaylistDto;
import com.rockstars.rockify.model.Playlist;
import com.rockstars.rockify.model.Song;
import com.rockstars.rockify.repository.PlaylistRepository;
import com.rockstars.rockify.repository.SongRepository;
import com.rockstars.rockify.util.PlaylistMapper;
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
@RequestMapping("/playlists")
public class PlaylistController extends BaseController {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    PlaylistController(PlaylistRepository playlistRepository, SongRepository songRepository) {
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
    }

    @GetMapping(value = "/all")
    ResponseEntity<List<PlaylistDto>> getAll() {
        return ResponseEntity.ok(playlistRepository.findAll().stream().map(PlaylistMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping(value = "/{playlistId}")
    ResponseEntity<PlaylistDto> get(@PathVariable Long playlistId) {
        Optional<Playlist> playlist = playlistRepository.findById(playlistId);
        return playlist.map(value -> ResponseEntity.ok(PlaylistMapper.toDto(value))).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    ResponseEntity<HttpStatus> post(@RequestBody PlaylistDto playlistDto) {
        Playlist playlist = PlaylistMapper.toEntity(playlistDto);
        playlistRepository.save(playlist);
        return getResponseWithStatus(HttpStatus.CREATED);
    }

    @DeleteMapping("/{playlistId}")
    ResponseEntity<HttpStatus> delete(@PathVariable Long playlistId) {
        if (playlistRepository.existsById(playlistId)) {
            playlistRepository.deleteById(playlistId);
            return getResponseWithStatus(HttpStatus.OK);
        }
        return getBadRequest();
    }

    @PutMapping("/addSong/{playlistId}/{songId}")
    ResponseEntity<HttpStatus> addSong(@PathVariable Long playlistId, @PathVariable Long songId)
    {
        Optional<Playlist> playlist = playlistRepository.findById(playlistId);
        Optional<Song> song = songRepository.findById(songId);

        if(playlist.isEmpty() || song.isEmpty())
        {
            return getBadRequest();
        }
        playlist.get().getSongs().add(song.get());
        playlistRepository.save(playlist.get());
        return getResponseWithStatus(HttpStatus.OK);
    }

}
