plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("tg-plugin")
    id("android-app-convention")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
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

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        }
    }
}

dependencies {
//    Hilt
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)

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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation( libs.androidx.material)
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

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
