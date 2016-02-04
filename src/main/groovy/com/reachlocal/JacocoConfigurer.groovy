package com.reachlocal

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.testing.jacoco.plugins.JacocoPlugin

class JacocoConfigurer extends Configurer {
    @Override
    Class<Plugin<Project>> getPluginTypeToConfigure() {
        JacocoPlugin
    }

    @Override
    void configureTask(Project project) {
    }

    @Override
    String getReportPath() {
        'build/reports/jacoco/test/html/index.html'
    }

    @Override
    String getReportTaskName() {
        'jacocoTestReport'
    }
}
