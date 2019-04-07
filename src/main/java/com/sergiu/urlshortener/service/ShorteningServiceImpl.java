package com.sergiu.urlshortener.service;

import java.math.BigInteger;
import java.util.Base64;

import javax.transaction.Transactional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sergiu.urlshortener.dto.UrlDTO;
import com.sergiu.urlshortener.entity.Url;
import com.sergiu.urlshortener.repository.UrlShorteningRepository;

@Component("defaultShorteningService")
@Transactional
public class ShorteningServiceImpl implements ShorteningService {

	@Autowired
	UrlShorteningRepository repository;

	@Override
	public UrlDTO shorten(UrlDTO urlDto) {
		Url existent = returnIfExists(urlDto.getUrl());
		if (existent != null) {
			return mapEntityToDto(existent);
		} else {
			return shorten(urlDto.getUrl());
		}
	}

	/**
	 * Looks provided url in DB and if
	 * exists it returns the shortened equivalent,
	 * else creates a new original - shortened pair
	 * Shortened version is the base64 url safe encoding
	 * of the database id.
	 * @param urlToShorten
	 * @return 
	 */
	private UrlDTO shorten(String urlToShorten) {
		Url url = new Url(urlToShorten);
		Url savedUrl = repository.save(url);
		if (savedUrl != null) {
			savedUrl.setShortenedUrl(Base64.getUrlEncoder().withoutPadding()
					.encodeToString(BigInteger.valueOf(savedUrl.getId()).toByteArray()));
			return mapEntityToDto(savedUrl);
		} else {
			throw new ServiceException("Could not shorten URL");
		}
	}

	public Url returnIfExists(String url) {
		Url urlFromDb = repository.findByOriginalUrl(url);
		if (urlFromDb != null) {
			return urlFromDb;
		}
		return null;
	}

	private UrlDTO mapEntityToDto(Url url) {
		UrlDTO dto = new UrlDTO();
		dto.setUrl(url.getShortenedUrl());
		return dto;
	}

	@Override
	public String getOriginalURL(String shortenedUrl) {
		Url url = repository.findByShortenedUrl(shortenedUrl);
		return url != null ? url.getOriginalURl() : null;
	}

}
