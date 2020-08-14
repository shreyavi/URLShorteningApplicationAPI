package com.example.URLShorteningAPI.controller;

import com.example.URLShorteningAPI.dto.LongURL;
import com.example.URLShorteningAPI.dto.ShortURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.URLShorteningAPI.service.URLShorteningService;
import java.net.URISyntaxException;
import java.net.URL;

@RestController
public class URLShorteningController {
    @Autowired
    private URLShorteningService urlShorteningService;
    private static final Logger LOG = LoggerFactory.getLogger(URLShorteningController.class);

    @RequestMapping(value = "/api/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ShortURL> create(@RequestBody LongURL longURL) {
            LOG.debug("Request to create a short url for long url {}.", longURL);
            URL shortURL =  urlShorteningService.getShortenedURL(longURL.getLongURL());
            return new ResponseEntity<>(new ShortURL(shortURL), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Void> redirect(@PathVariable String id) throws URISyntaxException {
        try {
            LOG.debug("Request to get the long url for ID {}", id);
            URL urlObj = urlShorteningService.getLongURL(id);
            LOG.debug("Return the long url {}", urlObj.toString());
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(urlObj.toURI())
                    .build();
        }
        catch (URISyntaxException ex){
            LOG.warn("Bad request {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
