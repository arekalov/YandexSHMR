plugins{
    `kotlin-dsl`
}

gradlePlugin {
       plugins.register("tg-plugin"){
        id = "tg-plugin"
        implementationClass = "com.arekalov.yandexshmr.plugins.TgPlugin"
    }
}

dependencies{
    implementation(libs.agp)
    implementation(libs.bundles.retrofit)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.kotlinx.coroutines.android)
}