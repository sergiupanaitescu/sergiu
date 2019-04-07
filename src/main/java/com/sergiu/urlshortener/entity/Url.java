package com.sergiu.urlshortener.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "url")
public class Url {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String shortenedUrl;

	private String originalUrl;

	public Url(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public Url() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShortenedUrl() {
		return shortenedUrl;
	}

	public void setShortenedUrl(String shortenedUrl) {
		this.shortenedUrl = shortenedUrl;
	}

	public String getOriginalURl() {
		return originalUrl;
	}

	public void setOriginalURl(String originalURl) {
		this.originalUrl = originalURl;
	}

}
