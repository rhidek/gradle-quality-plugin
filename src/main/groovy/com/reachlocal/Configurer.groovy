package com.reachlocal

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.Exec
import org.gradle.internal.os.OperatingSystem

abstract class Configurer {
    abstract Class<Plugin<Project>> getPluginTypeToConfigure()

    Task createConfigureTask(Project project) {
        project.plugins.apply(pluginTypeToConfigure)
        Task task = project.task("configure${pluginTypeToConfigure.simpleName}")
                           .doFirst({configureTask(project)})

        project.tasks.getByPath('compileJava').dependsOn(task)
        return task
    }
    abstract void configureTask(Project project)

    Task createViewTask(Project project) {
        project.task("${pluginTypeToConfigure.simpleName}View", type: Exec) {
            if(OperatingSystem.current().isMacOsX()) {
                commandLine 'open'
                args reportPath
            }
            else { // do nothing
                commandLine 'echo'
                args ' '
            }
        }
       .dependsOn(reportTaskName)
    }

    abstract String getReportPath()
    abstract String getReportTaskName()
}
