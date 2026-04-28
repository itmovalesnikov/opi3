#!/bin/sh
set -e
rm -rf lib/
mvn dependency:copy-dependencies -DoutputDirectory=lib/compile
mvn dependency:copy-dependencies -DoutputDirectory=lib/package -DincludeScope=runtime