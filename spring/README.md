# overlay-service

Showcase:
- Build image using
    - Dockerfile (500+mb)
        - docker build -t techdemo/spring-overlay-service .
    - Dockerfilelayer (276mb)
        - docker build -f Dockerfilelayer -t techdemolayer/spring-overlay-service .
    - Gradle (269mb)
        - `./gradlew bootBuildImage --imageName=techdemogradle/spring-overlay-service`
        - docker.io/paketobuildpacks/builder:base
        - docker.io/paketobuildpacks/run:base-cnb
        - See [springio-spring-boot-docker](https://reflectoring.io/spring-boot-docker/) or [reflectoring-spring-boot-docker](https://spring.io/guides/gs/spring-boot-docker/)
- Using `dive` tool
- Activate spring profile via environment variable in `docker-compose`
- Show logs via `docker logs spring-overlay-service`
- Externalizing properties: spring.data.mongodb.host = SPRING_DATA_MONGODB_HOST
- Auto generated docs - See `build\asciidoc\html5\index.html`
- gradle.properties in %GRADLE_USER_HOME% overrides project gradle.properties
	- gradle -q printProps
- Postman `localhost:8888/v1/overlay/create`

```js
{
    "name": "overlay123",
    "workspaceId": "111"
}
```

Build Image:

- gradle clean build
- docker build -t techdemo/spring-overlay-service .
- docker tag techdemo/spring-overlay-service techdemo/spring-overlay-service:0.0.1-S
- dive techdemo/spring-overlay-service

---

Build multiple layers so that rebuilding is much faster, and cache those that seldom change
	
```js
docker build -t techdemo/spring-overlay-service .
Sending build context to Docker daemon  29.92MB
Step 1/12 : FROM adoptopenjdk:11-jre-hotspot as builder
 ---> 39af3d85a52b
Step 2/12 : WORKDIR application
 ---> Running in 898233f9c63e
Removing intermediate container 898233f9c63e
 ---> 8be9f1599c7d
Step 3/12 : ARG JAR_FILE=build/libs/*.jar
 ---> Running in edcfd1550620
Removing intermediate container edcfd1550620
 ---> be464e2ce837
Step 4/12 : COPY ${JAR_FILE} application.jar
 ---> 6853bee2f147
Step 5/12 : RUN java -Djarmode=layertools -jar application.jar extract
 ---> Running in ef1dcc8be2fc
Removing intermediate container ef1dcc8be2fc
 ---> 9589ddc859a2
Step 6/12 : FROM adoptopenjdk:11-jre-hotspot
 ---> 39af3d85a52b
Step 7/12 : WORKDIR application
 ---> Using cache
 ---> 8be9f1599c7d
Step 8/12 : COPY --from=builder application/dependencies/ ./
 ---> 9c479f8ae4d9
Step 9/12 : COPY --from=builder application/spring-boot-loader/ ./
 ---> 3bf2d5a045f2
Step 10/12 : COPY --from=builder application/snapshot-dependencies/ ./
 ---> 1b28959a5d72
Step 11/12 : COPY --from=builder application/application/ ./
 ---> 895482ac97a4
Step 12/12 : ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
 ---> Running in 7a4b97e4656e
Removing intermediate container 7a4b97e4656e
 ---> 7befda2322e0
Successfully built 7befda2322e0
Successfully tagged techdemo/spring-overlay-service:latest
```
	
```js
docker build -t techdemo/spring-overlay-service .
Sending build context to Docker daemon  29.92MB
Step 1/12 : FROM adoptopenjdk:11-jre-hotspot as builder
 ---> 39af3d85a52b
Step 2/12 : WORKDIR application
 ---> Using cache
 ---> 8be9f1599c7d
Step 3/12 : ARG JAR_FILE=build/libs/*.jar
 ---> Using cache
 ---> be464e2ce837
Step 4/12 : COPY ${JAR_FILE} application.jar
 ---> e26d511fbce7
Step 5/12 : RUN java -Djarmode=layertools -jar application.jar extract
 ---> Running in 0880fb511842
Removing intermediate container 0880fb511842
 ---> 69fdaed02871
Step 6/12 : FROM adoptopenjdk:11-jre-hotspot
 ---> 39af3d85a52b
Step 7/12 : WORKDIR application
 ---> Using cache
 ---> 8be9f1599c7d
Step 8/12 : COPY --from=builder application/dependencies/ ./
 ---> Using cache
 ---> 9c479f8ae4d9
Step 9/12 : COPY --from=builder application/spring-boot-loader/ ./
 ---> Using cache
 ---> 3bf2d5a045f2
Step 10/12 : COPY --from=builder application/snapshot-dependencies/ ./
 ---> Using cache
 ---> 1b28959a5d72
Step 11/12 : COPY --from=builder application/application/ ./
 ---> 0235a0d8878d
Step 12/12 : ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
 ---> Running in efc3e8a4a477
Removing intermediate container efc3e8a4a477
 ---> c9eab495a632
Successfully built c9eab495a632
Successfully tagged techdemo/spring-overlay-service:latest
```