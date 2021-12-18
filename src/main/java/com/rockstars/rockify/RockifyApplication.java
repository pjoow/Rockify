package com.rockstars.rockify;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockstars.rockify.dto.BandDto;
import com.rockstars.rockify.dto.SongDto;
import com.rockstars.rockify.model.Band;
import com.rockstars.rockify.model.Song;
import com.rockstars.rockify.repository.BandRepository;
import com.rockstars.rockify.repository.SongRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
@Log4j2
public class RockifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(RockifyApplication.class, args);
    }

    @Bean
    public CommandLineRunner importData(ResourceLoader resourceLoader, BandRepository bandRepository, SongRepository songRepository) {
        //TODO: verplaatsen naar andere class en opknippen in methods
        //TODO: misschien toch checken of iets al bestaat (obv rockstarID?)
        return (args) -> {

            ObjectMapper objectMapper = new ObjectMapper();

            // TODO <String, BandDto> is mooier
            Map<String, Long> bandList = Arrays.stream(objectMapper.readValue(resourceLoader.getResource("artists.json").getFile(), BandDto[].class)).collect(Collectors.toMap(BandDto::getName, BandDto::getId));
            List<SongDto> metalSongList = Arrays.stream(objectMapper.readValue(resourceLoader.getResource("songs.json").getFile(), SongDto[].class)).filter(song -> "Metal".equals(song.getGenre()).collect(Collectors.toList());


            Map<Long, Band> bandsSaved = new HashMap<>();
            List<Song> songsToSave = new ArrayList<>();

            metalSongList.forEach(songDto ->
            {
                Long bandId = bandList.get(songDto.getArtist());
                if(bandId == null)
                {
                    log.warn("Song with id " + songDto.getId() + " could not be saved; invalid band name");
                    return;
                }
                Band band;
                if(!bandsSaved.containsKey(bandId))
                {
                    band = new Band();
                    band.setRockstarId(bandId);
                    band.setName(songDto.getArtist());
                    bandRepository.save(band);
                    bandsSaved.put(bandId, band);
                }
                else
                {
                    band = bandsSaved.get(bandId);
                }

                // TODO Magic number
                if(songDto.getYear() < 2016)
                {
                    Song song = new Song();
                    song.setRockstarId(songDto.getId());
                    song.setAlbum(songDto.getAlbum());
                    song.setBand(band);
                    song.setBpm(songDto.getBpm());
                    song.setDuration(songDto.getDuration());
                    song.setGenre(songDto.getGenre());
                    song.setShortName(songDto.getShortname());
                    song.setSpotifyId(songDto.getSpotifyId());
                    song.setName(songDto.getName());
                    song.setYear(songDto.getYear());
                    //TODO maak mapper
                    songsToSave.add(song);
                }
            });

            songRepository.saveAll(songsToSave);

            log.info("Import done!");

        };
    }

}
