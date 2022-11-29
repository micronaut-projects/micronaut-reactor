# Micronaut Reactor

[![Maven Central](https://img.shields.io/maven-central/v/io.micronaut.reactor/micronaut-reactor.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.micronaut.reactor%22%20AND%20a:%22micronaut-reactor%22)
[![Build Status](https://github.com/micronaut-projects/micronaut-reactor/workflows/Java%20CI/badge.svg)](https://github.com/micronaut-projects/micronaut-reactor/actions)
[![Revved up by Gradle Enterprise](https://img.shields.io/badge/Revved%20up%20by-Gradle%20Enterprise-06A0CE?logo=Gradle&labelColor=02303A)](https://ge.micronaut.io/scans)

Micronaut Reactor adds support for [Project Reactor](https://projectreactor.io/) to a Micronaut 3.x or 2.x application. If you are 
using Micronaut 1.x you do not need this module.

## Documentation

See the [Documentation](https://micronaut-projects.github.io/micronaut-reactor/latest/guide/) for more information. 

See the [Snapshot Documentation](https://micronaut-projects.github.io/micronaut-reactor/snapshot/guide/) for the current development docs.

## Snapshots and Releases

Snaphots are automatically published to [JFrog OSS](https://oss.jfrog.org/artifactory/oss-snapshot-local/) using [Github Actions](https://github.com/micronaut-projects/micronaut-reactor/actions).

See the documentation in the [Micronaut Docs](https://docs.micronaut.io/latest/guide/index.html#usingsnapshots) for how to configure your build to use snapshots.

Releases are published to JCenter and Maven Central via [Github Actions](https://github.com/micronaut-projects/micronaut-reactor/actions).

A release is performed with the following steps:

* [Edit the version](https://github.com/micronaut-projects/micronaut-reactor/edit/master/gradle.properties) specified by `projectVersion` in `gradle.properties` to a semantic, unreleased version. Example `1.0.0`
* [Create a new release](https://github.com/micronaut-projects/micronaut-reactor/releases/new). The Git Tag should start with `v`. For example `v1.0.0`.
* [Monitor the Workflow](https://github.com/micronaut-projects/micronaut-reactor/actions?query=workflow%3ARelease) to check it passed successfully.
* Celebrate!
