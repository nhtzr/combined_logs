#!/bin/sh

if test -z "${SRC_DIR:-}"; then SRC_DIR=/src    ; fi
if test -z "${OUT_DIR:-}"; then OUT_DIR=/output ; fi

build_sources() {
  (
    cd "$SRC_DIR" || exit "$?"
    find . -type f -name '*.java' -exec javac -d "$OUT_DIR" {} +
  )
}

build_sources "$@"
