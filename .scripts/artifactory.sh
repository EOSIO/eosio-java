
BRANCH=$2



echo "$BRANCH" | egrep "^release/.+" > /dev/null
IS_RELEASE=$?

echo "$IS_RELEASE"

echo "$BRANCH" | egrep "^feature/.+" > /dev/null
IS_FEATURE=$?


if [ "$BRANCH" == "master" ]; then
  echo "Branch is master"
elif [ "$BRANCH" == "develop" ]; then
  echo "Branch is develop"
  export ARTIFACTORY_ENVIROMENT="android-libs-dev-local"
elif [ "$BRANCH" == "scratch" ]; then
  echo "Branch is scratch"
  export ARTIFACTORY_ENVIROMENT="android-libs-scratch-local"
elif [ "$IS_FEATURE" -eq 0 ]; then
  echo "Is feature"
  export ARTIFACTORY_ENVIROMENT="android-libs-feature-local"
elif [[ "$IS_RELEASE" -eq 0 ]]; then
  echo "Is Release"
  export ARTIFACTORY_ENVIROMENT="android-libs-staging-local"
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

