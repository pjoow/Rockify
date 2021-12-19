package com.rockstars.rockify.util;

import com.rockstars.rockify.dto.PlaylistDto;
import com.rockstars.rockify.model.Playlist;
import com.rockstars.rockify.model.Song;

import java.util.Set;
import java.util.stream.Collectors;

public class PlaylistMapper {

    public static PlaylistDto toDto (Playlist playlist)
    {
        PlaylistDto playlistDto = new PlaylistDto();
        playlistDto.setName(playlist.getName());
        playlistDto.setId(playlist.getId());

        Set<Song> songs = playlist.getSongs();
        playlistDto.setSongs(songs.stream().map(SongMapper::toDto).collect(Collectors.toList()));

        return playlistDto;
    }

    public static Playlist toEntity(PlaylistDto playlistDto)
    {
        Playlist playlist = new Playlist();
        playlist.setName(playlistDto.getName());
        playlist.setId(playlistDto.getId());
        return playlist;
    }
}
