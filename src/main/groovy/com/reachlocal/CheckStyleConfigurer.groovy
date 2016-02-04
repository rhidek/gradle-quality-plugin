package com.reachlocal

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.quality.CheckstylePlugin
import org.gradle.api.tasks.Exec
import org.gradle.internal.os.OperatingSystem

import java.nio.file.Files
import java.nio.file.Path

class CheckStyleConfigurer {
    static Task checkStyleTask(Project project) {
        project.plugins.apply(CheckstylePlugin)

        project.tasks.getByPath('compileJava')
               .doFirst(
                {
                    // Needs to be in this closure so it gets evaluated during execution phase rather than configuration phase.
                    // Otherwise builds won't be able to override configs
                    File confFile = project.file(project.quality.checkStyleConfig.configDestination)
                    project.checkstyle {
                        configFile = confFile
                        toolVersion = '6.15'
                    }
                    copyConfigFromJar(confFile, project.quality.checkStyleConfig.configSource)
                })

        setupCSViewTask(project)
                .dependsOn('checkstyleMain')
    }


    private static void copyConfigFromJar(File destination, String resourcePath) {
        Path destinationPath = destination.toPath()
        Files.createDirectories(destinationPath.getParent())
        Files.write(destinationPath, CheckStyleConfigurer.getClassLoader().getResource(resourcePath).bytes)
    }


    private static Task setupCSViewTask(Project project) {
        project.task('checkStyleView', type: Exec) {
            if(OperatingSystem.current().isMacOsX()) {
                commandLine 'open'
                args 'build/reports/checkstyle/main.html'
            }
            else { // do nothing
                commandLine 'echo'
                args ' '
            }
        }
    }

}
