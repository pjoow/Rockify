package com.rockstars.rockify.controller;

import com.rockstars.rockify.service.FillerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class FillerController extends BaseController {

    private final FillerService fillerService;

    public FillerController(FillerService fillerService) {
        this.fillerService = fillerService;
    }

    @PostMapping(value = "fillInitialData")
    ResponseEntity<HttpStatus> fillInitialData() {
        try {
            fillerService.fillInitialData();
        } catch (IOException e) {
            getResponseWithStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return getResponseWithStatus(HttpStatus.OK);
    }
}
