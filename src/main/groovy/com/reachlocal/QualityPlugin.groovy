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
        Task checkStyleTask = CheckStyleConfigurer.checkStyleTask(project)
        Task pmdTask = PmdConfigurer.setupPmd(project)
        Task jacocoTask = JacocoConfigurer.setupJacoco(project)
        project.task('quality')
               .dependsOn('build', checkStyleTask, pmdTask, jacocoTask)
    }
}
