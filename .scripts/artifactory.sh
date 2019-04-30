#!/bin/bash
set -e -o pipefail

#BUILDKITE_BRANCH=$ARTIFACTORY_ENVIROMENT

while true; do
  case $1 in
    -e | --env) ARTIFACTORY_ENVIROMENT="$2"; shift 2 ;;
    *) break ;;
  esac
done

echo "$ARTIFACTORY_ENVIROMENT"

if [ "$ARTIFACTORY_ENVIROMENT" == "develop" ]; then
  echo "This is a Develop build!"
  ARTIFACTORY_ENVIROMENT="android-libs-dev-local"

elif [ "$ARTIFACTORY_ENVIROMENT" == "release/*" ]; then
  echo "This is a Release build!"
  ARTIFACTORY_ENVIROMENT="android-libs-staging-local"

elif [ "$ARTIFACTORY_ENVIROMENT" == "master" ]; then
  echo "This is a Master build!"
  ARTIFACTORY_ENVIROMENT="android-libs-release-local"

elif [ "$ARTIFACTORY_ENVIROMENT" == "feature/*" ]; then
  echo "This is a Feature build!"
  ARTIFACTORY_ENVIROMENT="android-libs-feature-local"

elif [ "$ARTIFACTORY_ENVIROMENT" == "scratch" ]; then
  echo "This is a Local build!"
  ARTIFACTORY_ENVIROMENT="android-libs-scratch-local"

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

