import com.expediagroup.graphql.plugin.gradle.config.GraphQLSerializer
import com.expediagroup.graphql.plugin.gradle.graphql
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

fun properties(key: String) = providers.gradleProperty(key)

plugins {
    // Kotlin support
    alias(libs.plugins.kotlin)
    // Compose support
    alias(libs.plugins.compose)
    // Gradle Changelog Plugin
    alias(libs.plugins.changelog)
    // Kotlin linter
    alias(libs.plugins.detekt)
    // ktlint Plugin
    alias(libs.plugins.ktlint)
    // GraphQL
    alias(libs.plugins.graphql)
    kotlin("plugin.serialization") version libs.versions.serialization.get()
}

group = "com.christopherosthues"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.graphql.client)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter.core)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation(libs.junit.platform.launcher)
    testImplementation(libs.junit.platform.suite.engine)
    testImplementation(libs.mocking.mockk)
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.register("installGitHooks", Copy::class) {
    from(file("$rootDir/scripts/pre-commit"))
    into(file("$rootDir/.git/hooks"))
    fileMode = 0b0111101101
}

tasks.named("compileKotlin") {
    dependsOn("installGitHooks")
}

ktlint {
    verbose = true
    version = libs.versions.ktlint.get()
    outputToConsole = true
    coloredOutput = true
    reporters {
        reporter(ReporterType.CHECKSTYLE)
        reporter(ReporterType.JSON)
        reporter(ReporterType.HTML)
    }
}

detekt {
    toolVersion = libs.versions.detekt.get()
    config.from("config/detekt/detekt.yml")
    buildUponDefaultConfig = true
}

// Configure Gradle Changelog Plugin - read more: https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    groups = listOf("Added", "Changed", "Removed", "Fixed")
    repositoryUrl = properties("pluginRepositoryUrl")
}

// Kotlin DSL
tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        html.required = true
        html.outputLocation = file("build/reports/mydetekt.html")
        md.required = false
        sarif.required = false
        txt.required = false
        xml.required = false
    }
}

graphql {
    client {
        allowDeprecatedFields = false
        endpoint = "https://gitlab.com/api/graphql"
        packageName = "com.christopherosthues.starwarsprogressbarissuetracker.graphql"
        queryFileDirectory = "${project.projectDir}/src/main/resources/graphql"
        queryFiles =
            listOf(
                file("${project.projectDir}/src/main/resources/graphql/issues/IssueFragments.graphql"),
                file("${project.projectDir}/src/main/resources/graphql/issues/CreateIssueMutation.graphql"),
                file("${project.projectDir}/src/main/resources/graphql/issues/GetEditIssueQuery.graphql"),
                file("${project.projectDir}/src/main/resources/graphql/issues/GetInitialIssuesQuery.graphql"),
                file("${project.projectDir}/src/main/resources/graphql/issues/GetIssueQuery.graphql"),
                file("${project.projectDir}/src/main/resources/graphql/issues/GetNextIssuesQuery.graphql"),
                file("${project.projectDir}/src/main/resources/graphql/issues/GetProjectQuery.graphql"),
                file("${project.projectDir}/src/main/resources/graphql/issues/UpdateIssueMutation.graphql"),
            )
        serializer = GraphQLSerializer.KOTLINX
        timeout {
            connect = 5_000
            read = 15_000
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "starwars-progress-bar-issue-tracker"
            packageVersion = "1.0.0"
        }
    }
}
