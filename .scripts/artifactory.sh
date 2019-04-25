#!/bin/bash
set -eu -o pipefail

export artifactory_contextURL="https://blockone.jfrog.io/blockone/webapp/#/login"
GRADLE_PROPERTIES="/var/lib/buildkite-agent/builds/automation-android-builder-fleet/EOSIO/eosio-java/eosiojava/gradle.properties"
export GRADLE_PROPERTIES
echo "Gradle Properties should exist at $GRADLE_PROPERTIES"
echo "Gradle Properties does not exist"
echo "Creating Gradle Properties file..."
touch $GRADLE_PROPERTIES
echo "Writing Secrets to gradle.properties..."
echo "artifactory_username=$(cat /var/lib/buildkite-agent/.artifactory-username)" >> $GRADLE_PROPERTIES
echo "artifactory_password=$(cat /var/lib/buildkite-agent/.artifactory-password)" >> $GRADLE_PROPERTIES
echo "artifactory_contextURL=https://blockone.jfrog.io/blockone" >> $GRADLE_PROPERTIES
echo "artifactory_repo=android-libs-dev" >> $GRADLE_PROPERTIES

