plugins {
    alias(libs.plugins.hoppswap.android.feature)
    alias(libs.plugins.hoppswap.android.library.compose)
}

android {
    namespace = "com.hoppswap.feature.auth"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.designsystem)
    implementation(projects.domain)

    testImplementation(libs.junit4)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
}