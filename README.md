# Build Info generator plugin for Spring Boot Actuator

This plugin generates properties file(s) that can be consumed by [Spring Boot Actuator Info endpoint](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html#production-ready-application-info)

-----
[![Plugin](https://img.shields.io/badge/Gradle%20Plugin-latest-green.svg)](https://plugins.gradle.org/plugin/com.pasam.gradle.buildinfo)
[![Build Status](https://travis-ci.org/spasam/spring-boot-build-info.svg?branch=master)](https://travis-ci.org/spasam/spring-boot-build-info)
-----

## Prerequisites

`Java` and `Spring Boot` gradle plugins are expected to be present in `build.gradle`. Example:

```groovy
plugins {
  ...
  id 'java'
  id 'org.springframework.boot' version '2.1.3.RELEASE'
  ...
}
```

## Usage

Add the following in your `build.gradle plugins` section:

```groovy
plugins {
  ...
  id 'com.pasam.gradle.buildinfo' version '0.1.1'
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
build.version = 0.1.1
build.time = 2019-03-17T22:42:50.131910Z
build.source-compatibility = 11
build.target-compatibility = 11
build.user-name = spasam
build.java-vendor = Oracle Corporation
build.java-version = 11.0.1
build.os-arch = x86_64
build.os-name = Mac OS X
build.os-version = 10.14.3
build.dependencies.spring-boot = 2.1.3.RELEASE
build.dependencies.activation = 1.1
...
```

With Spring Boot actuator enabled, actuator should serve up `git` and `build-info` properties as JSON. Accessing `<host>:<mgmg-port>/<actuator-info-endpoint>` should dump JSON similar to:

```json
{
  "git": {
    "branch": "master",
    "build": {
      "time": "2019-03-17T23:29:20Z",
      "version": "0.1.1",
      "user": {
        "name": "Seshu Pasam",
        "email": "email@example.com"
      },
      "host": "my-host"
    },
    "commit": {
      "time": "2019-03-15T22:32:25Z",
      "message": {
        "full": "Commit message full",
        "short": "Commit message fshort"
      },
      "id": {
        "describe": "",
        "abbrev": "14b1431",
        "full": "14b1431cfee81908c1ca70bda79b9e28625cfbd2"
      },
      "user": {
        "email": "email@example.com",
        "name": "Seshu Pasam"
      }
    },
    "dirty": "true",
    "tags": "1.30.0-rc6,1.30.0-rc7",
    "total": {
      "commit": {
        "count": "28"
      }
    },
    "closest": {
      "tag": {
        "commit": {
          "count": ""
        },
        "name": ""
      }
    },
    "remote": {
      "origin": {
        "url": "git@github.com:spasam/spring-boot-build-info"
      }
    }
  },
  "build": {
    "dependencies": {
      "spring-boot": "2.1.3.RELEASE",
      "activation": "1.1"
    },
    "os-arch": "x86_64",
    "version": "0.1.1",
    "user-name": "spasam",
    "os-name": "Mac OS X",
    "source-compatibility": "11",
    "java-vendor": "Oracle Corporation",
    "name": "spring-boot-build-info",
    "time": "2019-03-17T23:29:19.531Z",
    "artifact": "spring-boot-build-info",
    "group": "com.pasam",
    "java-version": "11.0.1",
    "target-compatibility": "11",
    "os-version": "10.14.3"
  }
}
```
