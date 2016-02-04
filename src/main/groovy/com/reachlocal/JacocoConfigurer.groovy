package com.reachlocal

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.Exec
import org.gradle.internal.os.OperatingSystem
import org.gradle.testing.jacoco.plugins.JacocoPlugin

class JacocoConfigurer {

    private static Task setupJacoco(Project project) {
        project.plugins.apply(JacocoPlugin)
        setupJacocoViewTask(project).dependsOn('jacocoTestReport')
    }

    private static Task setupJacocoViewTask(Project project) {
        project.task('jacocoView', type: Exec) {
            if(OperatingSystem.current().isMacOsX()) {
                commandLine 'open'
                args 'build/reports/jacoco/test/html/index.html'
            }
            else { // do nothing
                commandLine 'echo'
                args ' '
            }
        }
    }

}
