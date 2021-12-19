package com.rockstars.rockify.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {

    protected ResponseEntity<HttpStatus> getResponseWithStatus(HttpStatus status)
    {
        return new ResponseEntity<>(status);
    }

    protected ResponseEntity<HttpStatus> getBadRequest()
    {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
