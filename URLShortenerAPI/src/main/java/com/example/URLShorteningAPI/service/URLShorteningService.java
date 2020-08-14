package com.example.URLShorteningAPI.service;

import com.example.URLShorteningAPI.model.URLInfo;
import com.example.URLShorteningAPI.repository.URLRepository;
import com.example.URLShorteningAPI.exception.URLShorteningBadRequestException;
import com.example.URLShorteningAPI.exception.URLShorteningNotFoundException;
import com.example.URLShorteningAPI.util.Base62Conversion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class URLShorteningService {
    @Autowired
    private Base62Conversion base62Conversion;
    @Autowired
    private URLRepository urlRepository;
    @Autowired
    private URL baseURL;

    private static final Logger LOG = LoggerFactory.getLogger(URLShorteningService.class);

    @Transactional
    public URL getShortenedURL(String url){
        try{
            final URL longURL = new URL(url);
            URLInfo urlObj = urlRepository.findByLongURL(longURL);
            return urlObj != null && urlObj.isActive() ? new URL(baseURL.getProtocol(), baseURL.getHost(), baseURL.getPort(), "/"+urlObj.getID())
                    : new URL(baseURL.getProtocol(), baseURL.getHost(), baseURL.getPort(), "/"+createShortenedURL(longURL));
        }
        catch (MalformedURLException ex) {
            LOG.warn("Url cannot be shortened for {}", url);
            throw new URLShorteningBadRequestException("Url cannot be shortened for " + url);
        }
        catch(NullPointerException e) {
            LOG.warn("Url not found for {}", url);
            throw new URLShorteningNotFoundException("Short Url not found for " + url);
        }
    }

    @Transactional
    public URL getLongURL(String Id){
        try{
            final URLInfo shortURLInfo = urlRepository.findById(Id).orElseThrow(() -> new URLShorteningNotFoundException(Id));
            if(shortURLInfo.isActive()){
                updateURLInfo(shortURLInfo);
                LOG.debug("Return long url.");
                return shortURLInfo.getLongURL();
            }
            LOG.debug("Url is Exhausted for {}", Id);
            throw new URLShorteningNotFoundException("Url does not exist for the ID " + Id);
        }
        catch(NullPointerException ex){
            LOG.debug("Url does not exist for {}", Id);
            throw new URLShorteningNotFoundException("Url does not exist for the ID " + Id);
        }
    }

    private String getUniqueID(){
        String id = base62Conversion.encode();
        int retryAttempt = 0;
        while(urlRepository.findById(id).isPresent()){
            id = base62Conversion.encode();
            if(retryAttempt == 20 ) throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
            retryAttempt++;
        }
        LOG.debug("Unique index is {}", id);
        return id;
    }

    private String createShortenedURL(URL longURL){
        String id = getUniqueID();
        final URLInfo shortURLInfo = new URLInfo(
                id,
                longURL,
                0,
                true
        );
        urlRepository.save(shortURLInfo);
        LOG.debug("Short url is added to DB.");
        return shortURLInfo.getID();
    }

    private void updateURLInfo(URLInfo shortURLInfo){
            int numClicks = shortURLInfo.getClickCount() + 1;
            LOG.debug("Update url count to {}.", numClicks);
            shortURLInfo.setClickCount(numClicks);
            if(numClicks == 10) shortURLInfo.setActive(false);
            urlRepository.save(shortURLInfo);
    }
}
