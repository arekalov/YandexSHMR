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
import javax.inject.Inject

abstract class ValidateSizeTask @Inject constructor(
    private val tgApi: TelegramApi
) : DefaultTask() {

    @get:InputDirectory
    abstract val apkDir: DirectoryProperty

    @get:Input
    abstract val token: Property<String>

    @get:Input
    abstract val apkFileSizeLimit: Property<Int>

    @get:Input
    abstract val chatId: Property<String>


    @TaskAction
    fun execute() {
        apkDir.get().asFile.listFiles()
            ?.filter { it.name.endsWith(".apk") }
            ?.forEach { apkFile ->
                runBlocking {
                    try {
                        val length = apkFile.length().bytesToMegaBytes()
                        if (length > apkFileSizeLimit.get()) {
                            tgApi.sendMessage(
                                message = "Built apk file is larger than ${apkFileSizeLimit.get()}MB. Error",
                                token = token.get(),
                                chatId = chatId.get()
                            )
                            throw GradleException("ValidateTask failed: incorrect file size.")
                        }
                        project.extensions.extraProperties.set(
                            "SIZE",
                            String.format("%.1f", length).toFloat()
                        )
                    } catch (ex: Exception) {
                        throw GradleException("Unexpected error in ValidateSizeTask: $ex")
                    }
                }
            }
    }
}

fun Long.bytesToMegaBytes() = this / 1048576f