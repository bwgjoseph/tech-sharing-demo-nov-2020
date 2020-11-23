package com.armyc3.overlay.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.armyc3.overlay.domain.Overlay;
import com.armyc3.overlay.repository.OverlayRepository;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *
 * @author Joseph Gan
 * @since 1.0
 */
@Service
@AllArgsConstructor
public class OverlayService implements CrudService<Overlay> {

	private final OverlayRepository overlayRepository;
	
	@EventListener
	public void init(ApplicationReadyEvent event) {
		
		this.overlayRepository.deleteAll()
							.thenMany(
								Flux.just("self", "self2")
								.map(overlay -> Overlay.builder().name(overlay).workspaceId("1").build())
								.flatMap(this.overlayRepository::save)
							)
							.thenMany(this.overlayRepository.findAll())
							.subscribe(System.out::println);
	}
	
	/**
	 * @param overlay the overlay
	 * @return the created overlay
	 */
	@Override
	public Mono<Overlay> create(Overlay overlay) {
		return this.overlayRepository.save(overlay);
	}
	
	/**
	 * @param id the overlay id
	 * @return the associated overlay
	 */
	@Override
	public Mono<Overlay> findById(String id) {
		return this.overlayRepository.findById(id);
	}

	/**
	 * @param name the overlay name
	 * @return the associated overlay
	 */
	@Override
	public Mono<Overlay> findByName(String name) {
		return this.overlayRepository.findByName(name);
	}

	/**
	 * @return all overlays
	 */
	@Override
	public Flux<Overlay> findAll() {
		return this.overlayRepository.findAll();
	}

	/**
	 * @param overlay the overlay
	 * @return the updated overlay
	 */
	@Override
	public Mono<Overlay> update(Overlay overlay) {
		return this.overlayRepository.findById(overlay.getId())
				.then(this.overlayRepository.save(overlay));
	}

	/**
	 * @param id the overlay id
	 * @return the deleted overlay
	 */
	@Override
	public Mono<Overlay> delete(String id) {
		return this.overlayRepository.findById(id)
					.flatMap(oldValue ->
						this.overlayRepository.deleteById(id)
					.then(Mono.just(oldValue)));
	}
}