import com.android.build.gradle.BaseExtension

fun BaseExtension.baseAndroidConfig() {
    namespace = AndroidConst.NAMESPACE
    setCompileSdkVersion(AndroidConst.COMPILE_SKD)
    defaultConfig {
        minSdk = AndroidConst.MIN_SKD

        vectorDrawables {
            useSupportLibrary = true
        }
    }
    compileOptions {
        sourceCompatibility = AndroidConst.COMPILE_JDK_VERSION
        targetCompatibility = AndroidConst.COMPILE_JDK_VERSION
    }
    kotlinOptions {
        jvmTarget = AndroidConst.KOTLIN_JVM_TARGET
    }
}