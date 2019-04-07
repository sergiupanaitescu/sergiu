package com.sergiu.urlshortener;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.sergiu.urlshortener.dto.UrlDTO;
import com.sergiu.urlshortener.service.ShorteningService;

/**
 * @author Sergiu Panaitescu
 *
 */
@SpringBootApplication
@ComponentScan({"com.sergiu.urlshortener.service", "com.sergiu.urlshortener.repository"})
@EnableJpaRepositories("com.sergiu.urlshortener.repository")
@RestController
public class URLShorteningApi {

	@Autowired
	@Qualifier("defaultShorteningService")
	private ShorteningService shorteningService;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(URLShorteningApi.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "8180"));
		app.run(args);
	}

	/**
	 * End point for shortening provided url.
	 * If provided url is already in the database it will
	 * return the already existing shortened pair and not
	 * a new one.
	 * @param UrlDto
	 * @return UrlDto
	 */
	@PostMapping(path = "/shorten", consumes = "application/json", produces = "application/json")
	public UrlDTO shortenUrl(@RequestBody UrlDTO urlDto) {
		return shorteningService.shorten(urlDto);
	}

	/**
	 * Used to redirect shortened url
	 * to original url
	 * @param shortenedUrl
	 * @return
	 */
	@GetMapping(path = "{shortenedUrl}")
	public RedirectView redirectWithUsingRedirectPrefix(@PathVariable String shortenedUrl) {
		String originalUrl = shorteningService.getOriginalURL(shortenedUrl);
		RedirectView redirectView = new RedirectView();
		if (originalUrl != null) {
			redirectView.setUrl(originalUrl);
		} else {
			redirectView.setStatusCode(HttpStatus.NOT_FOUND);
		}
		return redirectView;
	}
}
