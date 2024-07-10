package com.arekalov.yandexshmr.plugins

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.Variant
import com.arekalov.yandexshmr.plugins.api.TelegramApi
import com.arekalov.yandexshmr.plugins.tasks.TgTask
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.configurationcache.extensions.capitalized

class TgPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val androidComponents =
            project.extensions.findByType(AndroidComponentsExtension::class.java)
                ?: throw GradleException("Android plugin required.")

        val ext = project.extensions.create("tgPlugin", TgExtension::class.java)

        val api = TelegramApi.create()
        androidComponents.onVariants { variant: Variant ->
            val artifacts = variant.artifacts.get(SingleArtifact.APK)
            project.tasks.register(
                "tgReportFor${variant.name.capitalized()}",
                TgTask::class.java,
                api
            ).configure {
                apkDir.set(artifacts)
                token.set(ext.token)
                chatId.set(ext.chatId)
            }
        }
    }
}

interface TgExtension {
    val token: Property<String>
    val chatId: Property<String>
}

