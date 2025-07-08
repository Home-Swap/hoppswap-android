plugins {
    alias(libs.plugins.hoppswap.android.library)
    alias(libs.plugins.hoppswap.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.hoppswap.data.profile"
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(projects.core.network)
    implementation(projects.core.common)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    testImplementation(libs.hilt.android.testing)
    kspTest(libs.hilt.compiler)
}