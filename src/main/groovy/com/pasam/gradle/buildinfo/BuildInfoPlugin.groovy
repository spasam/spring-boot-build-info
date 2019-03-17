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

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin

import com.gorylenko.GitPropertiesPlugin

class BuildInfoPlugin implements Plugin<Project> {
  @Override
  void apply(Project project) {
    if (!project.plugins.hasPlugin(GitPropertiesPlugin)) {
      project.logger.info('Applying git properties plugin')
      project.plugins.apply(GitPropertiesPlugin)
    }

    project.tasks.create(BuildInfoTask.NAME, BuildInfoTask)
  }
}
