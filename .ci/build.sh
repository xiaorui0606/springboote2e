#!/bin/bash

#
# The purpose of this script is to prepare the environment for building packages
#

export JAVA_VERSION=${JAVA_VERSION:-17}

if [[ -f "/etc/mariner-release" ]]; then
  tdnf install -y msopenjdk-${JAVA_VERSION}
  tdnf install -y maven
fi

echo "Java version: " $(java -version)
echo "Maven version: " $(mvn -v)
echo "Python version: " $(py --version)
echo "Pip version: " $(pip --version)
echo "Helm version: " $(helm version)


export MAVEN_OPTS="-Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=WARN -Dorg.slf4j.simpleLogger.showDateTime=true"
mvn -e -B -X -Pci -U "${MAVEN_OPTS}" clean compile
