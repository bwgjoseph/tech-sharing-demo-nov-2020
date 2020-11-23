package com.armyc3.overlay.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.armyc3.overlay.domain.Overlay;
import com.armyc3.overlay.repository.OverlayRepository;
import com.armyc3.overlay.service.OverlayService;

import reactor.core.publisher.Mono;

/**
 * @author Joseph Gan
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
public class OverlayServiceTest {

	@Autowired
	private OverlayService overlayService;
	
	@MockBean
	private OverlayRepository overlayRepository;
	
//	@Test
	@DisplayName("test save overlay")
	public void saveOverlay() {
		
		Overlay overlay = new OverlayFactory().GetOverlayInstance(UUID.randomUUID().toString());
		
		when(overlayRepository.save(any(Overlay.class)))
				.thenReturn(
						Mono.just(overlay));
		
		this.overlayService.create(overlay);
		
		verify(overlayRepository, times(1)).save(any(Overlay.class));
	}
	
	// If not using @Profile, and start the application (not test), Spring will load 
	// this bean and detects 2 OverlayService bean
	// Currently, using @Profile to resolve this, but do look into more correct solution
	@TestConfiguration
	@Profile("test")
	static class OverlayConfig {
		
		@Bean
		public OverlayService overlayService(OverlayRepository overlayRepository) {
			return new OverlayService(overlayRepository);
		}
	}
}
