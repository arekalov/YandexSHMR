package com.arekalov.yandexshmr.plugins.tasks

import com.arekalov.yandexshmr.plugins.api.TelegramApi
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import javax.inject.Inject

abstract class DetailInfoTask @Inject constructor(
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
        apkDir.get().asFile.listFiles()?.filter { it.name.endsWith(".apk") }
            ?.forEach { apk ->
                val zipFile = ZipFile(apk)
                val report = StringBuilder("APK content report:\n")

                zipFile.entries().asIterator().forEach { entry: ZipEntry ->
                    val sizeInKB = entry.size / (1024.0)
                    report.append("- ${entry.name} %.1f KB\n".format(sizeInKB))
                }
                val tempReportFile = File.createTempFile("apk_detail_info", ".txt")
                tempReportFile.writeText(report.toString())

                runBlocking {
                    try {
                        tgApi.upload(
                            file = tempReportFile,
                            chatId = chatId.get(),
                            token = token.get()
                        )
                    } catch (ex: Exception) {
                        println(ex)
                        tgApi.sendMessage(
                            message = "Failed to send APK content report",
                            token = token.get(),
                            chatId = chatId.get()
                        )
                    } finally {
                        if (tempReportFile.exists()) {
                            tempReportFile.delete()
                        }
                    }
                }
            }
    }
}
