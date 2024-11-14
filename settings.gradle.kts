pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "Stylish"
include(":app")
include(":login:data")
include(":login:domain")
include(":login:presentation")
include(":core:presentation")
include(":core:domain")
include(":core:data")
include(":products:data")
include(":products:domain")
include(":products:presentation")
include(":core:network")
include(":core:common-test")
