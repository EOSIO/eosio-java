#!/bin/bash
set -e -o pipefail

BRANCH=$1

echo "$BRANCH" | grep -e "^release/.+" > /dev/null
IS_RELEASE=$?

echo "$BRANCH" | grep -e "^feature/.+" > /dev/null
IS_FEATURE=$?


if [ "$BRANCH" == "develop" ]; then
  echo "This is a Develop build!"
  ARTIFACTORY_ENVIROMENT="android-libs-dev-local"

elif [[ "$IS_RELEASE" -eq 0 ]]; then
  echo "This is a Release build!"
  ARTIFACTORY_ENVIROMENT="android-libs-staging-local"

elif [ "$BRANCH" == "master" ]; then
  echo "This is a Master build!"
  ARTIFACTORY_ENVIROMENT="android-libs-release-local"

elif [ "$IS_FEATURE" -eq 0 ]; then
  echo "Is feature"
  ARTIFACTORY_ENVIROMENT="android-libs-feature-local"

elif [ "$BRANCH" == "scratch" ]; then
  echo "This is a Local build!"
  ARTIFACTORY_ENVIROMENT="android-libs-scratch-local"

else
    echo "BAD"
fi


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
echo "artifactory_repo=$ARTIFACTORY_ENVIROMENT" >> $GRADLE_PROPERTIES

