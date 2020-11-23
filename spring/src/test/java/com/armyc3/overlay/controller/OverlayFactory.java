/**
 * 
 */
package com.armyc3.overlay.controller;

import java.util.List;

import com.armyc3.overlay.domain.Overlay;

import reactor.core.publisher.Flux;

/**
 * @author gboonwei
 *
 */
public class OverlayFactory {

	public Overlay GetOverlayDefaultInstance() {
		return Overlay.builder()
				.name("self")
				.workspaceId("1")
				.build();
	}
	
	public Overlay GetOverlayInstance(String id) {
		return Overlay.builder()
				.id(id)
				.name("self")
				.workspaceId("1")
				.build();
	}
	
	public Flux<Overlay> GetAllOverlays(List<String> uuids) {
		return Flux.fromIterable(uuids)
					.map(id -> Overlay.builder().id(id).name("a").workspaceId("1").build());
	}

}
