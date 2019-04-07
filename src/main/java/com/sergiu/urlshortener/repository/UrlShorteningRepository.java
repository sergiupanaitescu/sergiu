package com.sergiu.urlshortener.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sergiu.urlshortener.entity.Url;

@Repository
public interface UrlShorteningRepository extends CrudRepository<Url, Long> {

	Url findByOriginalUrl(String originalUrl);

	Url findByShortenedUrl(String shortenedUrl);

}
