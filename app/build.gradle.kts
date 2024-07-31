
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("tg-plugin")
    id("android-app-convention")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    id("de.mannodermaus.android-junit5") version "1.8.2.1"
}

val telegramBotToken: String = System.getenv("TELEGRAM_BOT_TOKEN") ?: "no token"
val telegramChatId: String = System.getenv("TELEGRAM_CHAT_ID") ?: "no chatId"
val oauthAuthorization = System.getenv("OAUTH_AUTHORIZATION") ?: "no oauth"
tgPlugin {
    token.set(telegramBotToken)
    chatId.set(telegramChatId)
    detailInfoEnabled.set(true)
}


android {
    namespace = "com.arekalov.yandexshmr"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.arekalov.yandexshmr"
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "OAUTH_AUTHORIZATION", "\"$oauthAuthorization\"")
        testInstrumentationRunner = "com.arekalov.yandexshmr.CustomAndroidTestRunner"
        vectorDrawables {
        }
    }

    signingConfigs {
        create("release") {
            keyAlias = "yandexshmr"
            keyPassword = "rx29na03"
            storeFile = file("/home/arekalov/Documents/key.jks")
            storePassword = "rx29na03"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            resValue("string", "clear_text_config", "false")
        }
        debug {
            resValue("string", "clear_text_config", "false")
        }
    }
}

dependencies {
    //    Hilt
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)

//    Navigation
    implementation(libs.androidx.navigation.compose)

//    Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.logging.interceptor)

//    WorkManager
    implementation(libs.androidx.work.runtime.ktx)

//    EncryptedSharedPreferences
    implementation(libs.androidx.security.crypto.ktx)

//    Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

//    DivKit
    implementation(libs.bundles.divkit)
    implementation(libs.glide)

//    Platform and ui
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

//   Testing
    testImplementation(libs.junit.jupiter.v5103)
    testRuntimeOnly(libs.junit.platform.launcher)
    testImplementation(libs.turbine)
    testImplementation(libs.kotlinx.coroutines.test.v173)
    implementation(libs.truth)
    testImplementation(libs.mockk)

    androidTestImplementation(libs.junit.jupiter.v5103)
    androidTestImplementation(libs.junit.platform.launcher)
    androidTestImplementation(libs.mockwebserver)

    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.android.compiler.v2511)


    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.test.manifest)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
