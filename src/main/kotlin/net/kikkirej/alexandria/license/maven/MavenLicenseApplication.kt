package net.kikkirej.alexandria.license.maven

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MavenLicenseApplication

fun main(args: Array<String>) {
	runApplication<MavenLicenseApplication>(*args)
}
