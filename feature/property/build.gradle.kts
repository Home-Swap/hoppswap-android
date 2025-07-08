plugins {
    alias(libs.plugins.hoppswap.android.feature)
    alias(libs.plugins.hoppswap.android.library.compose)
}

android {
    namespace = "com.hoppswap.feature.property"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.designsystem)
    implementation(projects.domain)
}