plugins {
    java
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.patrimonio"
version = "0.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")        // API REST (controllers)
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")   // Entidades + repositorios
    runtimeOnly("com.h2database:h2")                                          // Base de datos en memoria
    compileOnly("org.projectlombok:lombok")                                   // @Data: getters y setters
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
