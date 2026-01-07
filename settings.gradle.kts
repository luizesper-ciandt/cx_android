pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    includeBuild("react-native/node_modules/@react-native/gradle-plugin")
}

plugins {
    id("com.facebook.react.settings")
}

configure<com.facebook.react.ReactSettingsExtension> {
    autolinkLibrariesFromCommand(
        workingDirectory = file("react-native"),
        lockFiles = files(
            "react-native/package-lock.json",
            "react-native/package.json",
            "react-native/react-native.config.js"
        )
    )
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "cx_android"
include(":app")
