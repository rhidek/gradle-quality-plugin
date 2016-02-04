package com.reachlocal

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.quality.PmdPlugin

class PmdConfigurer extends Configurer {
    @Override
    Class<Plugin<Project>> getPluginTypeToConfigure() {
        PmdPlugin
    }

    @Override
    void configureTask(Project project) {
        project.pmd {
            ignoreFailures = !project.quality.pmdConfig.failBuildOnViolation
        }
    }

    @Override
    String getReportPath() {
        'build/reports/pmd/main.html'
    }

    @Override
    String getReportTaskName() {
        'pmdMain'
    }
}
