plugins {
    alias(libs.plugins.hoppswap.android.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.hoppswap.domain"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.data.auth)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    testImplementation(libs.hilt.android.testing)
    kspTest(libs.hilt.compiler)

    testImplementation(libs.junit4)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
}