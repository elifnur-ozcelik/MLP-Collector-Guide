pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google() // <-- This line is crucial
        mavenCentral()
    }
}
rootProject.name = "ElifnurOzcelik_HW1"
include(":app")
