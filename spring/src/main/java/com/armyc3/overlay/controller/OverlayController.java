package com.armyc3.overlay.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.armyc3.overlay.domain.Overlay;
import com.armyc3.overlay.service.OverlayService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * @author Joseph Gan
 * @since 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/overlay")
public class OverlayController {

	private final OverlayService overlayService;
	
	/**
	 * @param overlay the overlay
	 * @return the created overlay
	 */
	@PostMapping(value = "/create")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Mono<Overlay> create(@Valid @RequestBody Overlay overlay) {
		return this.overlayService.create(overlay);
	}
	
	/**
	 * @param id the overlay id
	 * @return the associated overlay
	 */
	@GetMapping(value = "/id/{id}")
	public Mono<Overlay> findById(@PathVariable String id) {
		return this.overlayService.findById(id);
	}
	
	/**
	 * @param name the overlay name
	 * @return the associated overlay
	 */
	@GetMapping(value = "/name/{name}")
	public Mono<Overlay> findByName(@PathVariable String name) {
		return this.overlayService.findByName(name);
	}
	
	/**
	 * @return all overlays
	 */
	@GetMapping(value = "/all")
	public Flux<Overlay> findAll() {
		return this.overlayService.findAll();
	}
	
	/**
	 * @param overlay the overlay
	 * @return the updated overlay
	 */
	@PutMapping(value = "/update")
	public Mono<Overlay> update(@Valid @RequestBody Overlay overlay) {
		return this.overlayService.update(overlay);
	}
	
	/**
	 * @param id the overlay id
	 * @return the deleted overlay
	 */
	@DeleteMapping(value = "/delete/{id}")
	public Mono<Overlay> delete(@PathVariable String id) {
		return this.overlayService.delete(id);
	}
}