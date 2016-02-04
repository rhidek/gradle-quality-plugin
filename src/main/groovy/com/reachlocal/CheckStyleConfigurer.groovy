package com.reachlocal

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.quality.CheckstylePlugin

import java.nio.file.Files
import java.nio.file.Path

class CheckStyleConfigurer extends Configurer {
    @Override
    Class<Plugin<Project>> getPluginTypeToConfigure() {
        CheckstylePlugin
    }

    @Override
    void configureTask(Project project) {
        File confFile = project.file(project.quality.checkStyleConfig.configDestination)
        project.checkstyle {
            configFile = confFile
            toolVersion = '6.15'
        }
        copyConfigFromJar(confFile, project.quality.checkStyleConfig.configSource)
    }

    private static void copyConfigFromJar(File destination, String resourcePath) {
        Path destinationPath = destination.toPath()
        Files.createDirectories(destinationPath.getParent())
        Files.write(destinationPath, CheckStyleConfigurer.getClassLoader().getResource(resourcePath).bytes)
    }

    @Override
    String getReportPath() {
        'build/reports/checkstyle/main.html'
    }

    @Override
    String getReportTaskName() {
        'checkstyleMain'
    }
}
