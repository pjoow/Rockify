package com.rockstars.rockify;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockstars.rockify.dto.SongDto;
import com.rockstars.rockify.model.Band;
import com.rockstars.rockify.repository.BandRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class IntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BandRepository bandRepository;

    @Before
    public void initialize()
    {
        Band band = new Band();
        band.setName("Spinal Tap");
        band.setRockstarId(1363L);
        bandRepository.save(band);
        bandRepository.flush();
    }

    @Test
    public void testAddSong() throws Exception {

        SongDto songDto = getTestSong();
        mvc.perform(MockMvcRequestBuilders
                .post("/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getSongJson(songDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mvc.perform(MockMvcRequestBuilders.get("/songs/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("[" + getSongJson(songDto) + "]"));
    }

    @Test
    public void testAddSongInvalidBand() throws Exception {
        SongDto songDto = getTestSong();
        songDto.setBandId(1337L);
        mvc.perform(MockMvcRequestBuilders
                .post("/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getSongJson(songDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testUpdateSong() throws Exception {

        SongDto songDto = getTestSong();
        mvc.perform(MockMvcRequestBuilders
                .post("/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getSongJson(songDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());

        songDto.setBpm(100000);

        mvc.perform(MockMvcRequestBuilders
                .put("/songs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getSongJson(songDto)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        mvc.perform(MockMvcRequestBuilders.get("/songs/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().json("[" + getSongJson(songDto) + "]"));
    }



    private SongDto getTestSong() {
        return new SongDto(1L, "Programmeren is cool", 2010, "Spinal Tap", 1L, "pm", 200, 1789089, "Metal", "12398712387", "Programmeer album", null);
    }

    private String getSongJson(SongDto songDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(songDto);
    }
}
