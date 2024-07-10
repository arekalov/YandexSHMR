package com.arekalov.yandexshmr.plugins.tasks

import com.arekalov.yandexshmr.plugins.api.TelegramApi
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class TgTask @Inject constructor(
    private val tgApi: TelegramApi
) : DefaultTask() {

    @get:InputDirectory
    abstract val apkDir: DirectoryProperty

    @get:Input
    abstract val token: Property<String>

    @get:Input
    abstract val chatId: Property<String>

    @TaskAction
    fun execute() {
        apkDir.get().asFile.listFiles()
            ?.filter { it.name.endsWith(".apk") }
            ?.forEach { apkFile ->
                runBlocking {
                    try {
                        tgApi.upload(apkFile, token.get(), chatId.get())
                        tgApi.sendMessage("APK uploaded: ${apkFile.name}", token.get(), chatId.get())
                    } catch (ex: Exception) {
                        println(ex)
                        tgApi.sendMessage("Failed to upload APK", token.get(), chatId.get())
                    }
                }
            }
    }
}
