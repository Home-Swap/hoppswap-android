import com.hoppswap.HoppSwapBuildType

plugins {
    alias(libs.plugins.hoppswap.android.application)
    alias(libs.plugins.hoppswap.android.application.compose)
    alias(libs.plugins.hoppswap.android.application.flavors)
    alias(libs.plugins.hoppswap.android.application.firebase)
    alias(libs.plugins.hoppswap.hilt)
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.hoppswap"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.hoppswap"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        debug {
            applicationIdSuffix = HoppSwapBuildType.DEBUG.applicationIdSuffix
        }
        release {
            isMinifyEnabled = true
            applicationIdSuffix = HoppSwapBuildType.RELEASE.applicationIdSuffix
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))

            // To publish on the Play store a private signing key is required, but to allow anyone
            // who clones the code to sign and run the release variant, use the debug signing key.
            // TODO: Abstract the signing configuration to a separate file to avoid hardcoding this.
            signingConfig = signingConfigs.named("debug").get()
            // Ensure Baseline Profile is fresh for release builds.
            baselineProfile.automaticGenerationDuringBuild = true
        }
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.designsystem)
    implementation(projects.data.auth)

    implementation(projects.feature.auth)
    implementation(projects.feature.home)
    implementation(projects.feature.profile)
    implementation(projects.feature.property)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.hilt.navigation.compose)
}

baselineProfile {
    // Don't build on every iteration of a full assemble.
    // Instead enable generation directly for the release build variant.
    automaticGenerationDuringBuild = false

    // Make use of Dex Layout Optimizations via Startup Profiles
    dexLayoutOptimization = true
}

dependencyGuard {
    configuration("prodReleaseRuntimeClasspath")
}
