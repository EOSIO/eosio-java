#!/usr/bin/env bash
set -eu -o pipefail

 export artifactory_contextURL="https://blockone.jfrog.io/blockone"

    GRADLE_PROPERTIES="$BUILDKITE_HOME/EOSIO/eosio-java/eosiojava/gradle.properties"
    export GRADLE_PROPERTIES

    echo "Gradle Properties should exist at $GRADLE_PROPERTIES"
        echo "Gradle Properties does not exist"

        echo "Creating Gradle Properties file..."
        touch $GRADLE_PROPERTIES

        echo "Writing Secrets to gradle.properties..."
        echo "artifactory_username=(cat $BUILDKITE_HOME/.artifactory-username)" >> $GRADLE_PROPERTIES
        echo "artifactory_password=(cat $BUILDKITE_HOME/.artifactory-password)" >> $GRADLE_PROPERTIES
        echo "artifactory_contextURL=$artifactory_contextURL" >> $GRADLE_PROPERTIES
        echo "$artifactory_contextURL" >> $GRADLE_PROPERTIES

