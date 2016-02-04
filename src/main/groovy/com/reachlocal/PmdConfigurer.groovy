package com.reachlocal

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.quality.PmdPlugin
import org.gradle.api.tasks.Exec
import org.gradle.internal.os.OperatingSystem

class PmdConfigurer {
    static Task setupPmd(Project project) {
        project.plugins.apply(PmdPlugin)

        project.tasks.getByPath('pmdMain')
               .doFirst(
                {
                    // Needs to be in this closure so it gets evaluated during execution phase rather than configuration phase.
                    // Otherwise builds won't be able to override configs
                    project.pmd {
                        ignoreFailures = !project.quality.pmdConfig.failBuildOnViolation
                    }
                })

        setupPmdViewTask(project).dependsOn('pmdMain')
    }

    private static Task setupPmdViewTask(Project project) {
        project.task('pmdView', type: Exec) {
            if(OperatingSystem.current().isMacOsX()) {
                commandLine 'open'
                args 'build/reports/pmd/main.html'
            }
            else { // do nothing
                commandLine 'echo'
                args ' '
            }
        }
    }
}
