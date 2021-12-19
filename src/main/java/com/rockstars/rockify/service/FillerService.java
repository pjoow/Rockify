package com.rockstars.rockify.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockstars.rockify.dto.BandDto;
import com.rockstars.rockify.dto.SongDto;
import com.rockstars.rockify.model.Band;
import com.rockstars.rockify.model.Song;
import com.rockstars.rockify.repository.BandRepository;
import com.rockstars.rockify.repository.PlaylistRepository;
import com.rockstars.rockify.repository.SongRepository;
import com.rockstars.rockify.util.BandMapper;
import com.rockstars.rockify.util.SongMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
public class FillerService {

    private final SongRepository songRepository;
    private final BandRepository bandRepository;
    private final PlaylistRepository playlistRepository;

    private static final int MAX_YEAR = 2015;
    private static final String GENRE = "Metal";
    private static final String ARTIST_FILE = "artists.json";
    private static final String SONG_FILE = "songs.json";

    public FillerService(SongRepository songRepository, BandRepository bandRepository, PlaylistRepository playlistRepository) {
        this.songRepository = songRepository;
        this.bandRepository = bandRepository;
        this.playlistRepository = playlistRepository;

    }

    public void fillInitialData() throws IOException {
        clearData();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, BandDto> bandnameDtoList = Arrays.stream(objectMapper.readValue(getFile(ARTIST_FILE), BandDto[].class))
                .collect(Collectors.toMap(BandDto::getName, Function.identity()));
        List<SongDto> metalSongList = Arrays.stream(objectMapper.readValue(getFile(SONG_FILE), SongDto[].class))
                .filter(song -> GENRE.equals(song.getGenre()))
                .collect(Collectors.toList());

        Map<Long, Band> bandsSaved = new HashMap<>();
        List<Song> songsToSave = new ArrayList<>();
        metalSongList.forEach(songDto ->
        {
            BandDto bandDto = bandnameDtoList.get(songDto.getArtist());
            if (bandDto == null) {
                log.warn("Song with id " + songDto.getId() + " could not be saved; invalid band name");
                return;
            }
            Band band;
            if (!bandsSaved.containsKey(bandDto.getId())) {
                band = BandMapper.toEntity(bandDto);
                bandRepository.save(band);
                bandsSaved.put(bandDto.getId(), band);
            } else {
                band = bandsSaved.get(bandDto.getId());
            }

            if (songDto.getYear() <= MAX_YEAR) {
                songsToSave.add(SongMapper.toEntity(songDto, band));
            }
        });
        songRepository.saveAll(songsToSave);
        log.info("Import done!");
    }

    private void clearData() {
        songRepository.deleteAll();
        bandRepository.deleteAll();
        playlistRepository.deleteAll();
    }

    private File getFile(String file) throws IOException {
        return new ClassPathResource(file).getFile();
    }
}
