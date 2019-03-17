# Build Info generator plugin for Spring Boot Actuator

This plugin generates properties file(s) that can be consumed by [Spring Boot Actuator Info endpoint](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html#production-ready-application-info)

-----
[![Plugin](https://img.shields.io/badge/Gradle%20Plugin-latest-green.svg)](https://plugins.gradle.org/plugin/com.pasam.gradle.buildinfo)
-----

## Prerequisites

`Java` and `Spring Boot` gradle plugins are expected to be present in `build.gradle`. Example:

```groovy
plugins {
  ...
  id 'java'
  id 'org.springframework.boot' version '2.1.2.RELEASE'
  ...
}
```

## Usage

Add the following in your `build.gradle plugins` section:

```groovy
plugins {
  ...
  id 'com.pasam.gradle.buildinfo' version '0.1.0'
  ...
}
```

## Output

This plugin also depends on [gradle-git-properties](https://github.com/n0mer/gradle-git-properties) plugin version `2.0.0`.

During the build, `git.properties` will be generated in build resources directory. `git.properties` should look like:

```properties
git.branch=master
git.build.host=my-host
git.build.time=2019-03-17T18\:25\:23-0400
git.build.user.email=email@example.com
git.build.user.name=Seshu Pasam
git.build.version=unspecified
git.closest.tag.commit.count=
git.closest.tag.name=
git.commit.id=2f4f4f52af30ea10d4dbfe0489aad924a0a86009
git.commit.id.abbrev=2f4f4f5
git.commit.id.describe=
git.commit.message.full=Commit message full
git.commit.message.short=Commit message short
git.commit.time=2019-03-13T11\:45\:48-0400
git.commit.user.email=email@users.noreply.github.com
git.commit.user.name=Seshu Pasam
git.dirty=true
git.remote.origin.url=git@github.com\:spasam/spring-boot-build-info
git.tags=1.29.0
git.total.commit.count=25
```

`build-info.properties` file will also be generated in build resources META-INF directory. Contents of this properties file should look like:

```properties
build.artifact = spring-boot-build-info
build.name = spring-boot-build-info
build.group = com.pasam
build.version = 0.1.0
build.time = 2019-03-17T22:42:50.131910Z
build.source-compatibility = 11
build.target-compatibility = 11
build.user-name = spasam
build.java-vendor = Oracle Corporation
build.java-version = 11.0.1
build.os-arch = x86_64
build.os-name = Mac OS X
build.os-version = 10.14.3
build.dependencies.spring-boot = 2.1.2.RELEASE
build.dependencies.activation = 1.1
...
```