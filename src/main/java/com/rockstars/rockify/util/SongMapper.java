package com.rockstars.rockify.util;

import com.rockstars.rockify.dto.SongDto;
import com.rockstars.rockify.model.Band;
import com.rockstars.rockify.model.Song;

import java.util.Optional;

public class SongMapper {

    public static SongDto toDto (Song song)
    {
        SongDto songDto = new SongDto();
        songDto.setRockstarId(song.getRockstarId());
        songDto.setAlbum(song.getAlbum());
        songDto.setArtist(song.getBand().getName());
        songDto.setBpm(song.getBpm());
        songDto.setDuration(song.getDuration());
        songDto.setGenre(song.getGenre());
        songDto.setShortname(song.getShortName());
        songDto.setSpotifyId(song.getSpotifyId());
        songDto.setName(song.getName());
        songDto.setYear(song.getYear());
        songDto.setId(song.getId());
        songDto.setBandId(song.getBand().getId());

        return songDto;
    }

    public static Song toEntity (SongDto songDto, Band band)
    {
        Song song = new Song();
        song.setRockstarId(songDto.getRockstarId());
        song.setAlbum(songDto.getAlbum());
        song.setBand(band);
        song.setBpm(songDto.getBpm());
        song.setDuration(songDto.getDuration());
        song.setGenre(songDto.getGenre());
        song.setShortName(songDto.getShortname());
        song.setSpotifyId(songDto.getSpotifyId());
        song.setName(songDto.getName());
        song.setYear(songDto.getYear());

        return song;
    }

    public static Song copy (Song song, SongDto songDto, Band band)
    {
        song.setYear(songDto.getYear());
        song.setName(songDto.getName());
        song.setGenre(songDto.getGenre());
        song.setBpm(songDto.getBpm());
        song.setAlbum(songDto.getAlbum());
        song.setDuration(songDto.getDuration());
        song.setBand(band);
        return song;
    }
}
