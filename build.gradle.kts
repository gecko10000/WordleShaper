plugins {
    id("java")
    application
}

sourceSets {
    main {
        java {
            srcDir("src")
        }
        resources {
            srcDir("res")
        }
    }
}

group = "gecko10000.wordleshaper"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {}

application {
    mainClass.set("gecko10000.wordleshaper.WordleShaper")
}
