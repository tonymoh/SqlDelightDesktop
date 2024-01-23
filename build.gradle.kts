import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("com.squareup.sqldelight")
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

val sqlDelightVersion = "1.5.3"

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("com.squareup.sqldelight:sqlite-driver:$sqlDelightVersion")
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "SqlDelightDesktop"
            packageVersion = "1.0.0"
        }
    }
}
sqldelight {
    database("BpDatabase") { // This will be the name of the generated database class.
        packageName = "com.db.BpDatabase"
    }
}