package com.rockstars.rockify.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Band {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //TODO gaat dit goed met bestaande ID's in JSON?
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "band")
    private List<Song> songs;

    private Long rockstarId;

}
