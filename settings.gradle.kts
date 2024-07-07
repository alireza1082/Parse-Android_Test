pluginManagement {
    repositories {
        maven {
            url = java.net.URI(settings.extra["pluginRepoUrl"] as String)
            isAllowInsecureProtocol = true
        }
        maven {
            url = java.net.URI(settings.extra["mavenRepoUrl"] as String)
            isAllowInsecureProtocol = true
        }
        maven {
            url = java.net.URI(settings.extra["googleRepoUrl"] as String)
            isAllowInsecureProtocol = true
        }
        maven {
            url = java.net.URI(settings.extra["gradlePluginRepoUrl"] as String)
            isAllowInsecureProtocol = true
        }
        maven { url = java.net.URI("https://jitpack.io") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        googleMirror()
        mavenMirror()
        jcenterMirror()
        maven { url = java.net.URI("https://jitpack.io") }
    }
}

// Mirror repositories on artifactory
fun RepositoryHandler.jcenterMirror() = setRepo(settings.extra["jcenterRepoUrl"] as String)
fun RepositoryHandler.mavenMirror() = setRepo(settings.extra["mavenRepoUrl"] as String)
fun RepositoryHandler.googleMirror() = setRepo(settings.extra["googleRepoUrl"] as String)

fun RepositoryHandler.setRepo(uri: String) {
    maven {
        if (settings.extra.has("mavenUsername") && settings.extra.has("mavenPassword")) {
            val mavenUsername: String = settings.extra["mavenUsername"] as String
            val mavenPassword: String = settings.extra["mavenPassword"] as String

            credentials {
                username = mavenUsername
                password = mavenPassword
            }
        }

        this.url = java.net.URI(uri)
        isAllowInsecureProtocol = true
    }
}

rootProject.name = "ParseTest"
include(":app")
