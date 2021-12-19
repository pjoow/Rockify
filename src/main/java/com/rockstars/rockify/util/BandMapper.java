package com.rockstars.rockify.util;

import com.rockstars.rockify.dto.BandDto;
import com.rockstars.rockify.model.Band;

public class BandMapper {

    public static BandDto toDto (Band band)
    {
        BandDto bandDto = new BandDto();
        bandDto.setName(band.getName());
        bandDto.setId(band.getId());
        bandDto.setRockstarId(band.getRockstarId());
        return bandDto;
    }

    public static Band toEntity (BandDto bandDto)
    {
        Band band = new Band();
        band.setName(bandDto.getName());
        band.setRockstarId(bandDto.getRockstarId());
        return band;
    }
}
