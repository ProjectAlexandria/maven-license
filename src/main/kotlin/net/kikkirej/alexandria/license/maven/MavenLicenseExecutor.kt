package net.kikkirej.alexandria.license.maven

import net.kikkirej.alexandria.license.maven.db.MavenDependency
import net.kikkirej.alexandria.license.maven.db.MavenDependencyRepository
import net.kikkirej.alexandria.license.maven.db.MavenRule
import net.kikkirej.alexandria.license.maven.db.MavenRuleRepository
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskHandler
import org.camunda.bpm.client.task.ExternalTaskService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@ExternalTaskSubscription("maven-license-analysis")
class MavenLicenseExecutor(@Autowired val mavenDependencyRepository: MavenDependencyRepository,
                           @Autowired val mavenRuleRepository: MavenRuleRepository,
): ExternalTaskHandler {

    val log =  LoggerFactory.getLogger(javaClass)

    override fun execute(externalTask: ExternalTask?, externalTaskService: ExternalTaskService?) {
        val dependencies =
            mavenDependencyRepository.getDependenciesForAnalysisId(externalTask!!.businessKey.toLong())
        log.info("checking analysis ${externalTask.businessKey}: $dependencies")
        for (dependency in dependencies){
            val rule = getRuleForDependency(dependency.groupId, false)
            log.info("identified license rule $rule for $dependency")
            if(rule != null && dependency.license != rule.license){
                dependency.license = rule.license
                mavenDependencyRepository.save(dependency)
            }
        }
        externalTaskService!!.complete(externalTask)
    }

    private fun getRuleForDependency(groupId: String, inherited: Boolean): MavenRule? {
        val ruleOptional = mavenRuleRepository.findByGroupIdPrefix(groupId)
        if(ruleOptional.isPresent && ((ruleOptional.get().inheritance && inherited) || !inherited)){
           return ruleOptional.get()
        }
        val upperGroupId = getUpperGroupId(groupId)
        if (upperGroupId.isEmpty()){
            return null
        }
        return getRuleForDependency(upperGroupId, true)
    }


    private fun getUpperGroupId(groupId: String): String{
        val lastIndexDot = groupId.lastIndexOf(".")
        if(lastIndexDot<=0){
            return ""
        }
        return groupId.substring(0, lastIndexDot-1)
    }
}