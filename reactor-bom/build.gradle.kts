plugins {
    id("io.micronaut.build.internal.bom")
}
micronautBom {
    suppressions {
        acceptedVersionRegressions.add("reactor-compat")
        acceptedLibraryRegressions.add("reactor")
    }
}
