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

val artifactName = "WordleShaper"
val groupName = "gecko10000.wordleshaper"
group = groupName
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "$groupName.$artifactName"
    }
}

application {
    mainClass.set("$groupName.$artifactName")
}
