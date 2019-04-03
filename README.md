# [Aura-Development-Bot](https://github.com/AuraDevelopmentTeam/Aura-Development-Bot)

[![Build Status](https://gitlab.aura-dev.team/AuraDev/Aura-Development-Bot/badges/master/build.svg)](https://gitlab.aura-dev.team/AuraDev/Aura-Development-Bot/pipelines)
[![Coverage Report](https://gitlab.aura-dev.team/AuraDev/Aura-Development-Bot/badges/master/coverage.svg)](https://gitlab.aura-dev.team/AuraDev/Aura-Development-Bot/pipelines)

Simple plugin to limit how many of a type of block a player can place

## Downloads

You can download all builds from either:

- Maven:
  - Releases: https://maven.aura-dev.team/repository/auradev-releases/
  - Snapshots: https://maven.aura-dev.team/repository/auradev-snapshots/

## [Issue Reporting](https://github.com/AuraDevelopmentTeam/Aura-Development-Bot/issues)

If you found a bug or even are experiencing a crash please report it so we can fix it. Please check at first if a bug report for the issue already
[exits](https://github.com/AuraDevelopmentTeam/Aura-Development-Bot/issues). If not just create a
[new issue](https://github.com/AuraDevelopmentTeam/Aura-Development-Bot/issues/new) and fill out the form.

Please include the following:

* Aura-Development-Bot version
* Java version/build
* For crashes:
  * Steps to reproduce
  * Any logs

*(When creating a new issue please follow the template)*

## [Feature Requests](https://github.com/AuraDevelopmentTeam/Aura-Development-Bot/issues)

If you want a new feature added, go ahead an open a [new issue](https://github.com/AuraDevelopmentTeam/InvSync/Aura-Development-Bot/new), remove the existing
form and describe your feature the best you can. The more details you provide the easier it will be implementing it.  
You can also talk to us on [Discord](https://discord.me/bungeechat).

## Developing with our Bot

So you want to add support or even develop an addon for our plugin then you can easily add it to your development environment! All releases get uploaded to my
maven repository. (Replace `{version}` with the appropriate version!)

### Maven

    <repositories>
        <repository>
            <id>AuraDevelopmentTeam/id>
            <url>https://maven.aura-dev.team/repository/auradev-releases/</url>
            <!--<url>https://maven.aura-dev.team/repository/auradev-snapshots/</url>-->
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>dev.aura.developmentbot</groupId>
            <artifactId>Aura-Development-Bot</artifactId>
            <version>{version}</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>

### Gradle

    repositories {
        maven {
            name "AuraDevelopmentTeam"
            url "https://maven.aura-dev.team/repository/auradev-releases/"
            // url "https://maven.aura-dev.team/repository/auradev-snapshots/"
        }
    }

    dependencies {
        provided "dev.aura.developmentbot:Aura-Development-Bot:{version}"
    }

## Setting up a Workspace/Compiling from Source

* Clone: Clone the repository like this: `git clone --recursive https://github.com/AuraDevelopmentTeam/Aura-Development-Bot.git`
* IDE-Setup: Run [gradle] in the repository root: `./gradlew installLombok <eclipse|idea>`
* Build: Run [gradle] in the repository root: `./gradlew build`. The build will be in `build/libs`
* If obscure Gradle issues are found try running `./gradlew cleanCache clean`

You can run the project directly through [gradle]. Just run: `./gradlew run`

You can also pass parameters to the program: `./gradlew run "-Pargs=<param1> <param2> ... <paramN>"`

## Development builds

Between each offical release there are several bleeding edge development builds, which you can also use. But be aware that they might contain unfinished
features and therefore won't work properly.

You can find the builds here: https://gitlab.aura-dev.team/AuraDev/Aura-Development-Bot/pipelines

On the right is a download symbol, click that a dropdown will open. Select "build". Then you'll download a zip file containing all atrifacts including the
plugin jar.

## Signing

### PGP Signing

All files will be signed with PGP.  
The public key to verify it can be found in `keys/publicKey.asc`. The signatures of the files will also be found in the maven.

### Jar Signing

All jars from all official download sources will be signed. The signature will always have a SHA-1 hash of `2238d4a92d81ab407741a2fdb741cebddfeacba6` and you
are free to verify it.

## License

AuraBlockLimit is licensed under the [MIT License](https://opensource.org/licenses/MIT)

## Random Quote

> Normal people believe that if it ain’t broke, don’t fix it. Engineers believe that if it ain’t broke, it doesn’t have enough features yet.
>
> -- <cite>Scott Adams</cite>
