package net.kikkirej.alexandria.license.maven

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskHandler
import org.camunda.bpm.client.task.ExternalTaskService
import org.springframework.stereotype.Component

@Component
@ExternalTaskSubscription("maven-license-analysis")
class MavenLicenseExecutor: ExternalTaskHandler {
    override fun execute(externalTask: ExternalTask?, externalTaskService: ExternalTaskService?) {
        TODO("Not yet implemented")
    }
}