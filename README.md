# Gradle Quality Plugin
The purpose of this plugin is to provide an easy way to get CheckStyle, PMD, and Jacoco configured and running in a project.
The starting checkstyle.xml is a copy of the [one](https://raw.githubusercontent.com/checkstyle/checkstyle/master/src/main/resources/google_checks.xml) used by Google.
## Example Usage
```
buildscript{
    repositories{
        flatDir{
            dirs "file:///path/to/built/artifact/quality-plugin/1.0"
        }
    }
    dependencies{
        classpath('com.reachlocal:quality-plugin:1.0')
    }
}
apply plugin: 'quality'

quality{
    checkStyle{
        configDestination = "somewhere/other/than/default"
    }
    pmd {
        failBuildOnViolation = true
    }
}
```