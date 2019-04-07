package com.sergiu.urlshortener.service;

import com.sergiu.urlshortener.dto.UrlDTO;

public interface ShorteningService {

	UrlDTO shorten(UrlDTO urlDTO);

	String getOriginalURL(String shortenedUrl);
}
