import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

dependencies {
    implementation(projects.shared)

    implementation(compose.desktop.currentOs)
    implementation(libs.kotlinx.coroutinesSwing)
    implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")
    implementation(libs.compose.uiToolingPreview)
    implementation("org.jetbrains.compose.components:components-splitpane-desktop:1.11.1")
    implementation("org.jetbrains.compose.components:components-resources:1.7.3")
    implementation(compose.components.resources)
//    implementation("dev.snipme:kodeview:0.9.0")
//    implementation("dev.snipme:highlights:1.1.0")
    implementation("com.gallatinapps.syntaxmp:syntaxmp:0.3.0")
//    implementation("com.github.komodgn.compose-codeview:compose:0.5.0")
//    implementation("com.github.komodgn.compose-codeview:core:0.5.0")
//    implementation("com.github.User:Repo:Tag")
}

//./gradlew createDistributable

compose.desktop {
    application {
        mainClass = "org.example.project.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.example.project"
            packageVersion = "1.0.0"
        }
    }
}