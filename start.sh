#!/usr/bin/env bash

set -e

export CLASSPATH_PREFIX=target/classes
readonly PROGDIR=$(dirname $0)
cd ${PROGDIR}
./target/bin/api-quickstart

