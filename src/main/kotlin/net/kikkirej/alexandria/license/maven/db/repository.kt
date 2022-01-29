package net.kikkirej.alexandria.license.maven.db

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.*

interface MavenDependencyRepository: CrudRepository<MavenDependency, Long>{

    @Query("select distinct md from maven_module_dependency mmd " +
            "join mmd.module mm " +
            "join mm.analysis a " +
            "join mmd.dependency md " +
            "where a.id = ?1")
    fun getDependenciesForAnalysisId(analysisId: Long) : Set<MavenDependency>
}

interface MavenRuleRepository : CrudRepository<MavenRule, Long> {
    fun findByGroupIdPrefix(groupIdPrefix: String): Optional<MavenRule>
}