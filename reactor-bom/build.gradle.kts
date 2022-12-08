plugins {
    id("io.micronaut.build.internal.bom")
}
micronautBom {
    suppressions {
        bomAuthorizedGroupIds.put("io.projectreactor:reactor-bom", setOf("org.reactivestreams"))
        acceptedVersionRegressions.addAll("reactor-compat")
        acceptedLibraryRegressions.addAll("reactor")
    }
}
