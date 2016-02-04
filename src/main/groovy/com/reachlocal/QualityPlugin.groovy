package com.reachlocal

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.JavaPlugin

class QualityPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.plugins.apply(JavaPlugin)
        project.extensions.create('quality', QualityExtension)

        createQualityTask(project)
    }

    private static void createQualityTask(Project project){
        List<Configurer> configurers = [new CheckStyleConfigurer(), new PmdConfigurer(), new JacocoConfigurer()]
        Set<Task> tasks = ['build'] as Set<Task>
        configurers.each{ Configurer configurer ->
            tasks << configurer.createConfigureTask(project)
            tasks << configurer.createViewTask(project)
        }
        project.task('quality').dependsOn = tasks
    }
}
