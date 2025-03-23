#!/bin/bash

if [ -z "$1" ]; then
  echo "Usage: $0 <path/to/ClassFile.class>"
  exit 1
fi

CLASSFILE="$1"

if [ ! -f "$CLASSFILE" ]; then
  echo "Error: File '$CLASSFILE' not found."
  exit 1
fi

DIR=$(dirname "$CLASSFILE")
FILENAME=$(basename "$CLASSFILE")
CLASSNAME="${FILENAME%.class}"

javap -v -cp "$DIR" "$CLASSNAME"