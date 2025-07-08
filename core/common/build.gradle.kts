plugins {
    alias(libs.plugins.hoppswap.android.library)
    alias(libs.plugins.hoppswap.hilt)
}

android {
    namespace = "com.hoppswap.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.retrofit.core)
    implementation(libs.androidx.lifecycle.viewModelCompose)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
}