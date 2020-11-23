package com.armyc3.overlay.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.armyc3.overlay.domain.Overlay;

import reactor.core.publisher.Mono;

/**
 * 
 * @author Joseph Gan
 * @since 1.0
 */
@Repository
public interface OverlayRepository extends ReactiveMongoRepository<Overlay, String> {

	Mono<Overlay> findByName(String name);
	
}