package com.rockstars.rockify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SongDto {

    @JsonProperty("Id")
    private Long id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Year")
    private int year;

    @JsonProperty("Artist")
    private String artist;

    @JsonProperty("BandId")
    private Long bandId;

    @JsonProperty("Shortname")
    private String shortname;

    @JsonProperty("Bpm")
    private int bpm;

    @JsonProperty("Duration")
    private int duration;

    @JsonProperty("Genre")
    private String genre;

    @JsonProperty("SpotifyId")
    private String spotifyId;

    @JsonProperty("Album")
    private String album;

    @JsonProperty("rockstarId")
    private Long rockstarId;
}
