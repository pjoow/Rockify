package com.rockstars.rockify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BandDto {

    @JsonProperty("Id")
    private Long id;

    @JsonProperty("Name")
    private String name;
}
