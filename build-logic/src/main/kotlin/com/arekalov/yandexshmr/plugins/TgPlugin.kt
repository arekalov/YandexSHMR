package com.arekalov.yandexshmr.plugins

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.Variant
import com.arekalov.yandexshmr.plugins.api.TelegramApi
import com.arekalov.yandexshmr.plugins.tasks.DetailInfoTask
import com.arekalov.yandexshmr.plugins.tasks.UploadTask
import com.arekalov.yandexshmr.plugins.tasks.ValidateSizeTask
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.configurationcache.extensions.capitalized

const val APK_FILE_SIZE_LIMIT_DEFAULT = 1000
const val IS_VALIDATE_FILE_SIZE_TASK_ENABLED = true
const val DETAIL_INFO_ENABLED = false

class TgPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val androidComponents =
            project.extensions.findByType(AndroidComponentsExtension::class.java)
                ?: throw GradleException("Android plugin required.")

        val ext = project.extensions.create("tgPlugin", TgExtension::class.java).apply {
            apkFileSizeLimit.convention(APK_FILE_SIZE_LIMIT_DEFAULT)
            validateFileSizeTaskEnabled.convention(IS_VALIDATE_FILE_SIZE_TASK_ENABLED)
            detailInfoEnabled.convention(DETAIL_INFO_ENABLED)
        }
        val api = TelegramApi.create()
        androidComponents.onVariants { variant: Variant ->
            val artifacts = variant.artifacts.get(SingleArtifact.APK)
            project.tasks.register(
                "validateApkSizeFor${variant.name.capitalized()}",
                ValidateSizeTask::class.java,
                api
            ).configure {
                apkDir.set(artifacts)
                apkFileSizeLimit.set(ext.apkFileSizeLimit)
                token.set(ext.token)
                chatId.set(ext.chatId)
            }

            project.tasks.register(
                "tgReportFor${variant.name.capitalized()}",
                UploadTask::class.java,
                api
            ).configure {
                apkDir.set(artifacts)
                variantString.set(variant.name.lowercase())
                token.set(ext.token)
                chatId.set(ext.chatId)
                validateFileSizeTaskEnabled.set(ext.validateFileSizeTaskEnabled)

            }

            project.tasks.register(
                "apkDetailInfoFor${variant.name.capitalized()}",
                DetailInfoTask::class.java,
                api
            ).configure {
                apkDir.set(artifacts)
                token.set(ext.token)
                chatId.set(ext.chatId)
            }

            if (ext.validateFileSizeTaskEnabled.get()) {
                project.afterEvaluate {
                    project.tasks.named("tgReportForRelease").configure {
                        dependsOn(project.tasks.named("validateApkSizeForRelease"))
                    }
                    project.tasks.named("tgReportForDebug").configure {
                        dependsOn(project.tasks.named("validateApkSizeForDebug"))
                    }
                }
            }
            if (ext.detailInfoEnabled.get()) {
                project.afterEvaluate {
                    project.tasks.named("tgReportForDebug").configure {
                        finalizedBy("apkDetailInfoForDebug")
                    }
                    project.tasks.named("tgReportForRelease").configure {
                        finalizedBy("apkDetailInfoForRelease")
                    }
                }
            }
        }
    }
}


interface TgExtension {
    val detailInfoEnabled: Property<Boolean>
    val validateFileSizeTaskEnabled: Property<Boolean>
    val token: Property<String>
    val chatId: Property<String>
    val apkFileSizeLimit: Property<Int>
}

