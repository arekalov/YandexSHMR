package com.arekalov.yandexshmr.plugins.tasks

import com.arekalov.yandexshmr.plugins.api.TelegramApi
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

abstract class UploadTask @Inject constructor(
    private val tgApi: TelegramApi
) : DefaultTask() {

    @get:InputDirectory
    abstract val apkDir: DirectoryProperty

    @get:Input
    abstract val token: Property<String>

    @get:Input
    abstract val chatId: Property<String>

    @get:Input
    abstract val validateFileSizeTaskEnabled: Property<Boolean>

    @get:Input
    abstract val variantString: Property<String>


    @TaskAction
    fun execute() {
        val versionCodeValue = project.extensions
            .getByType(com.android.build.gradle.internal.dsl.BaseAppModuleExtension::class.java)
            .defaultConfig.versionCode
        apkDir.get().asFile.listFiles()
            ?.filter { it.name.endsWith(".apk") }
            ?.forEach { apkFile ->
                val newFileName = "todolist-${variantString.get()}-${versionCodeValue}.apk"
                val renamedFile = File(apkFile.parentFile, newFileName)
                if (apkFile.renameTo(renamedFile)) {
                    runBlocking {
                        try {
                            val textToSend = if (validateFileSizeTaskEnabled.get()) {
                                "APK uploaded: ${renamedFile.name}\nSize: ${
                                    project.extensions.extraProperties.get(
                                        "SIZE"
                                    )
                                }MB"
                            } else {
                                "APK uploaded: ${renamedFile.name}"
                            }
                            tgApi.upload(renamedFile, token.get(), chatId.get())
                            tgApi.sendMessage(
                                message = textToSend,
                                token = token.get(),
                                chatId = chatId.get()
                            )
                        } catch (ex: Exception) {
                            tgApi.sendMessage("Failed to upload APK", token.get(), chatId.get())
                        }
                    }
                } else {
                    throw GradleException("Failed to rename APK file.")
                }
            }
    }
}
