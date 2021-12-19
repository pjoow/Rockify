package com.rockstars.rockify.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaylistDto {

    private Long id;

    private String name;

    private List<SongDto> songs;
}
