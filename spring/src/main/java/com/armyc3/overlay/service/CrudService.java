package com.armyc3.overlay.service;

import com.armyc3.overlay.domain.Overlay;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Generic Interface for CRUD Service with Type T
 *
 * @author Joseph Gan
 * @since 1.0
 */
public interface CrudService<T> {
	
	Mono<T> create(T entity);
	
	Mono<T> findById(String id);
	
	Mono<T> findByName(String name);
	
	Flux<Overlay> findAll();
	
	Mono<T> update(T entity);
	
	Mono<T> delete(String id);
}
