#!/bin/sh

if test -z "${OUT_DIR:-}"; then OUT_DIR=/output ; fi

java -classpath "${OUT_DIR}" mx.nhtzr.combinedlogs.CombinedLogs "$@"
