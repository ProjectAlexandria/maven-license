package net.kikkirej.alexandria.license.maven.db

import javax.persistence.*

@Entity(name = "analysis")
class Analysis(@Id var id: Long)

@Entity(name="maven_module")
class MavenModule(
    @Id var id: Long,
    @ManyToOne var analysis: Analysis,
)

@Entity(name = "maven_module_dependency")
class MavenModuleDependency(@Id var id: Long,
                            @ManyToOne var dependency: MavenDependency,
                            @ManyToOne var module: MavenModule,
)

@Entity(name = "maven_dependency")
class MavenDependency(@Id var id: Long = 0,
                      var groupId: String,
                      var artifactId: String,
)

@Entity(name = "license")
class License(@Id @GeneratedValue var id: Long = 0,
              var name: String,
              var licenseId: String,
              var url: String,
              var deprecated: Boolean,
              var osiApproved: Boolean,
              var spdx: Boolean
)
