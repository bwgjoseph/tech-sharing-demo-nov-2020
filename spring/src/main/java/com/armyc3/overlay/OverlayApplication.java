package com.armyc3.overlay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class OverlayApplication {

	public static void main(String[] args) {
		SpringApplication.run(OverlayApplication.class, args);
	}
	
//	@Bean
//	public CommandLineRunner init(MongoOperations mongo) {
//		
//		return (String... args) -> {
//			mongo.dropCollection(Overlay.class);
//			mongo.createCollection(Overlay.class, CollectionOptions.empty().size(1000000).capped());
//			
//			Flux.range(1, 10)
//				.map(i -> Overlay.builder().name("abc").workspaceId(i.toString()).build())
//				.doOnNext(mongo::save)
//				.blockLast(Duration.ofSeconds(5));
//		};
//	}
}
