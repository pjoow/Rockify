package com.rockstars.rockify.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Set;

@Entity
@Getter
@Setter
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Band band;

    private String name;

    private int year;

    private String shortName;

    private int bpm;

    private int duration;

    private String genre;

    private String spotifyId;

    private String album;

    private Long rockstarId;

    @ManyToMany
    private Set<Playlist> playlists;
}
