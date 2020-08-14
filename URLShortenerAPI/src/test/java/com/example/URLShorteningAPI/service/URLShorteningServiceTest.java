package com.example.URLShorteningAPI.service;

import com.example.URLShorteningAPI.model.URLInfo;
import com.example.URLShorteningAPI.exception.URLShorteningBadRequestException;
import com.example.URLShorteningAPI.exception.URLShorteningNotFoundException;
import com.example.URLShorteningAPI.repository.URLRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;

@WebMvcTest(URLShorteningService.class)
class URLShorteningServiceTest {

    @MockBean
    private URLRepository urlRepo;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private URLShorteningService urlShorteningService;

    private String longURL;
    private String id;

    @BeforeEach
    private void setUp() {
        longURL="https://www.amazon.com/gp/browse.html?node=19277531011";
        id = "9aFZIO";
    }

    @Test
    void getShortenURL_findByLongUrlReturnsObject() throws MalformedURLException {
        URLInfo urlObj = new URLInfo(
                id,
                new URL(longURL),
                0,
                true
        );
        Mockito.when(urlRepo.findByLongURL(isA(URL.class))).thenReturn(urlObj);
        URL result = urlShorteningService.getShortenedURL(longURL);
        URL expected = new URL("http://localhost:8080/9aFZIO");
        assertEquals(expected, result);

    }

    @Test
    void getShortenURL_findByLongUrlReturnsNull() {
        Mockito.when(urlRepo.findByLongURL(isA(URL.class))).thenReturn(null);
        URL result = urlShorteningService.getShortenedURL(longURL);
        assertNotNull(result);
    }

    @Test
    void getShortenURL_throwsMalformedURLException()  {
        assertThrows(URLShorteningBadRequestException.class, () -> urlShorteningService.getShortenedURL("select * from table urlinfo"));
    }

    @Test
    void getShortenURL_throwsNULLException() throws MalformedURLException {
        URLInfo urlObj = new URLInfo(
                id,
                new URL(longURL),
                0,
                null
        );
        Mockito.when(urlRepo.findByLongURL(isA(URL.class))).thenReturn(urlObj);
        assertThrows(URLShorteningNotFoundException.class, () -> urlShorteningService.getShortenedURL(longURL));
    }

    @Test
    void getLongUrl_IdExistsURLIsActive() throws MalformedURLException {
        URLInfo urlObj = new URLInfo(
                id,
                new URL(longURL),
                0,
                true
        );
        Mockito.when(urlRepo.findById(isA(String.class))).thenReturn(java.util.Optional.of(urlObj));
        URL result = urlShorteningService.getLongURL(id);
        assertEquals(new URL(longURL), result);
    }

    @Test
    void getLongUrl_IdExistsURLIsInActive() throws MalformedURLException {
        URLInfo urlObj = new URLInfo(
                id,
                new URL(longURL),
                0,
                false
        );
        Mockito.when(urlRepo.findById(isA(String.class))).thenReturn(java.util.Optional.of(urlObj));
        assertThrows(URLShorteningNotFoundException.class, () -> urlShorteningService.getLongURL(id));
    }

    @Test
    void getLongUrl_IdDoesNotExists() {
        Mockito.when(urlRepo.findById(isA(String.class))).thenReturn(null);
        assertThrows(URLShorteningNotFoundException.class, () -> urlShorteningService.getLongURL(id));
    }


}