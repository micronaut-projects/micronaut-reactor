plugins {
    id("io.micronaut.build.internal.bom")
}
micronautBom {
    suppressions {
        bomAuthorizedGroupIds.put("io.projectreactor:reactor-bom", setOf("org.reactivestreams"))
        acceptedVersionRegressions.addAll("reactor", "reactor-compat")
        acceptedLibraryRegressions.addAll("reactor", "reactor-core", "reactor-test")
    }
    catalog {
        // We want to be able to reference these versions from other modules that import the catalog of this project
        versionCatalog {
            version("reactor", "3.5.0")
            library("reactor-core", "io.projectreactor", "reactor-core").versionRef("reactor")
            library("reactor-test", "io.projectreactor", "reactor-test").versionRef("reactor")
        }
    }
}
