package com.example.URLShorteningAPI.repository;

import com.example.URLShorteningAPI.model.URLInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.net.URL;

@Repository
public interface URLRepository extends JpaRepository<URLInfo, String> {
   @Query("select u from urlinfo u where u.longURL = ?1 and u.isActive = 1")
   URLInfo findByLongURL(@Param("longurl") URL longurl);
}
