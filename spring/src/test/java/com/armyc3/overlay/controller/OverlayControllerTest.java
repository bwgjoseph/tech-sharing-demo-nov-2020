package com.armyc3.overlay.controller;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.armyc3.overlay.domain.Overlay;
import com.armyc3.overlay.repository.OverlayRepository;
import com.armyc3.overlay.service.OverlayService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AutoConfigureRestDocs
@WebFluxTest(OverlayController.class)
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
public class OverlayControllerTest {

	@Autowired
	private WebTestClient webTestClient;
	
	@MockBean
	public OverlayService overlayService;
	
	@MockBean
	public OverlayRepository overlayRepository;
	
	private FieldDescriptor[] overlayFD = new FieldDescriptor[] {
		fieldWithPath("id").description("Overlay id").ignored(),
        fieldWithPath("name").description("Overlay name").type(String.class.getSimpleName()),
        fieldWithPath("workspaceId").description("Overlay workspace id").type(String.class.getSimpleName())
	};
	
//	@BeforeEach
//	public void setUp(RestDocumentationContextProvider restDocumentation) {
//		this.webTestClient = WebTestClient.bindToApplicationContext(context)
//				.configureClient()
//				.baseUrl("http://localhost")
//				.filter(documentationConfiguration(restDocumentation)
//						.operationPreprocessors()
//							.withRequestDefaults(prettyPrint())
//							.withResponseDefaults(prettyPrint()))
//				.build();
//	}
	
	@Test
	@DisplayName("Create new overlay")
	public void whenCreateOverlayThenReturnCreatedOverlay() {
		
		String uuid = UUID.randomUUID().toString();
		Overlay overlay = new OverlayFactory().GetOverlayInstance(uuid);
		Mono<Overlay> monoOverlay = Mono.just(overlay);
		
		when(this.overlayService.create(Mockito.any())).thenReturn(monoOverlay);
		
		this.webTestClient
			.post()
			.uri("/v1/overlay/create")
			.body(monoOverlay, Overlay.class)
			.exchange()
			.expectStatus()
				.isCreated()
			.expectHeader()
				.contentType(MediaType.APPLICATION_JSON)
			.expectBody(Overlay.class)
				.isEqualTo(overlay)
			.consumeWith(document("{method-name}", requestFields(overlayFD)));
	}
	
	@Test
	@DisplayName("Get Overlay By Id")
	public void whenGetOverlayByIdThenReturnOverlay() {
		
		String uuid = UUID.randomUUID().toString();
		Overlay overlay = new OverlayFactory().GetOverlayInstance(uuid);
		Mono<Overlay> monoOverlay = Mono.just(overlay);
		
		when(this.overlayService.findById(Mockito.anyString())).thenReturn(monoOverlay);

		this.webTestClient
			.get()
			.uri("/v1/overlay/id/{id}", uuid)
			.exchange()
			.expectStatus()
				.isOk()
			.expectHeader()
				.contentType(MediaType.APPLICATION_JSON)
			.expectBody(Overlay.class)
				.isEqualTo(overlay)
			.consumeWith(document("{method-name}", pathParameters(
                    parameterWithName("id").description("Unique identifier of the Sales Rep responsible of the customer")),
					responseFields(overlayFD)
					
					));
	}
	
	@Test
	@DisplayName("Get Overlay By Name")
	public void whenGetOverlayByNameThenReturnOverlay() {
		
		String uuid = UUID.randomUUID().toString();
		Overlay overlay = new OverlayFactory().GetOverlayInstance(uuid);
		Mono<Overlay> monoOverlay = Mono.just(overlay);
		
		when(this.overlayService.findByName(Mockito.anyString())).thenReturn(monoOverlay);
		
		this.webTestClient
			.get()
			.uri("/v1/overlay/name/{name}", overlay.getName())
			.exchange()
			.expectStatus()
				.isOk()
			.expectHeader()
				.contentType(MediaType.APPLICATION_JSON)
			.expectBody(Overlay.class)
				.isEqualTo(overlay)
			.consumeWith(document("{method-name}", responseFields(overlayFD)));
	}
	
	@Test
	@DisplayName("Get all overlays")
	public void whenGetAllOverlaysThenReturnAllOverlays() {
		
		List<String> uuids = Flux.range(1, 5).map(i -> UUID.randomUUID().toString()).collectList().block();
		
		Flux<Overlay> overlays = new OverlayFactory().GetAllOverlays(uuids);
		List<Overlay> overlayList = overlays.collectList().block();
		
		when(this.overlayService.findAll()).thenReturn(overlays);
		
		this.webTestClient
			.get()
			.uri("/v1/overlay/all")
			.exchange()
			.expectStatus()
				.isOk()
			.expectHeader()
				.contentType(MediaType.APPLICATION_JSON)
			.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$").isNotEmpty()
				.jsonPath("$[0].id").isEqualTo(overlayList.get(0).getId())
				.jsonPath("$[0].name").isEqualTo(overlayList.get(0).getName())
				.jsonPath("$[0].workspaceId").isEqualTo(overlayList.get(0).getWorkspaceId())
				.jsonPath("$[5]").doesNotExist()
			.consumeWith(document("{method-name}", responseFields(fieldWithPath("[]").description("Array of overlays")).andWithPrefix("[].", overlayFD)));
		
	}
	
	@Test
	@DisplayName("Update overlay")
	public void whenUpdateSingleOverlayThenReturnUpdatedOverlay() {
		
		String uuid = UUID.randomUUID().toString();
		Overlay overlay = new OverlayFactory().GetOverlayInstance(uuid);
		Mono<Overlay> monoOverlay = Mono.just(overlay);
		
		when(this.overlayService.update(Mockito.any())).thenReturn(monoOverlay);
		
		this.webTestClient
			.put()
			.uri("/v1/overlay/update")
			.body(monoOverlay, Overlay.class)
			.exchange()
			.expectStatus()
				.isOk()
			.expectHeader()
				.contentType(MediaType.APPLICATION_JSON)
			.expectBody(Overlay.class)
				.isEqualTo(overlay)
			.consumeWith(document("{method-name}", requestFields(overlayFD)));
	}
	
	@Test
	@DisplayName("Delete overlay")
	public void whenDeleteSingleOverlayThenReturnDeletedOverlay() {
		
		String uuid = UUID.randomUUID().toString();
		Overlay overlay = new OverlayFactory().GetOverlayInstance(uuid);
		Mono<Overlay> monoOverlay = Mono.just(overlay);
		
		when(this.overlayService.delete(Mockito.any())).thenReturn(monoOverlay);
		
		this.webTestClient
			.delete()
			.uri("/v1/overlay/delete/{id}", uuid)
			.exchange()
			.expectStatus()
				.isOk()
			.expectHeader()
				.contentType(MediaType.APPLICATION_JSON)
			.expectBody(Overlay.class)
				.isEqualTo(overlay)
			.consumeWith(document("{method-name}", responseFields(overlayFD)));
	}
	
//	@TestConfiguration
//	static class CustomizationConfiguration implements RestDocsWebTestClientConfigurationCustomizer {
//	 
//	    @Override
//	    public void customize(WebTestClientRestDocumentationConfigurer configurer) {
//	        configurer.operationPreprocessors()
//	            .withRequestDefaults(prettyPrint())
//	            .withResponseDefaults(prettyPrint());
//	    }
//	}
}
