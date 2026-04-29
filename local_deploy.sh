#!/bin/sh
set -e
# mvn clean package && rsync -v --progress target/web3.war ~/wildfly-39.0.1.Final/standalone/deployments/ROOT.war
ant clean build && rsync -v --progress dist/web3.war ~/wildfly-39.0.1.Final/standalone/deployments/ROOT.war
