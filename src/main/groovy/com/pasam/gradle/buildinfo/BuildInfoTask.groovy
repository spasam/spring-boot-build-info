/**
 * Copyright 2019 Seshubabu Pasam
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pasam.gradle.buildinfo

import java.io.File
import java.time.Instant

import org.gradle.api.Project
import org.gradle.api.DefaultTask
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

class BuildInfoTask extends DefaultTask {
  static final String NAME = 'generateBuildInfo'

  private static final String[] SYSTEM_PROPERTIES = [
    'user.name', 'java.vendor', 'java.version', 'os.arch', 'os.name', 'os.version'
  ]

  private static final String[] ENV_PROPERTIES = [
    'BUILD_URL'
  ]

  BuildInfoTask() {
    project.afterEvaluate {
      dependsOn(JavaPlugin.PROCESS_RESOURCES_TASK_NAME)
      project.tasks.findByName(JavaPlugin.CLASSES_TASK_NAME).dependsOn(NAME)
    }
  }

  @TaskAction
  void exec() {
    def propsFile = getBuildInfoProperties()

    def artifactTask = project.tasks.findByName('bootWar')
    if (artifactTask == null) {
      artifactTask = project.tasks.findByName('bootJar')
    }

    propsFile.withWriter('UTF-8') { writer ->
      writer.writeLine('build.artifact = ' + (artifactTask != null ? artifactTask.getBaseName() : 'unspecified'))
      writer.writeLine('build.name = ' + project.name)
      writer.writeLine('build.group = ' + project.group)
      writer.writeLine('build.version = ' + project.version)
      writer.writeLine('build.time = ' + Instant.now())
      writer.writeLine('build.source-compatibility = ' + project.sourceCompatibility.toString())
      writer.writeLine('build.target-compatibility = ' + project.targetCompatibility.toString())

      SYSTEM_PROPERTIES.each {
        writer.writeLine('build.' + it.replace('.', '-') + ' = ' + System.getProperty(it))
      }
      ENV_PROPERTIES.each {
        def value = System.getenv(it)
        if (value != null) {
          writer.writeLine('build.' + it.toLowerCase() + ' = ' + value)
        }
      }

      project.sourceSets.main.runtimeClasspath.each {
        def parts = it.name.split('(?<=\\D)\\-(?=\\d)')
        if (parts.length == 2 && parts[1].endsWith('.jar')) {
          writer.writeLine('build.dependencies.' + parts[0].replace('.', '-') + ' = ' + parts[1].substring(0, parts[1].length() - 4))
        }
      }
    }
  }

  @OutputFile
  public File getBuildInfoProperties() {
    return new File(project.tasks.findByName(JavaPlugin.PROCESS_RESOURCES_TASK_NAME).destinationDir,
      'META-INF/build-info.properties')
  }
}
