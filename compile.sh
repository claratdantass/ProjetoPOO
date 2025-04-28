#!/bin/bash
JAVAFX_LIB="/caminho/para/javafx-sdk-17.0.15/lib"
JAR="postgresql-42.7.5.jar"
SRC="src/main/java"
OUT="out"

javac --module-path "$JAVAFX_LIB" --add-modules javafx.controls,javafx.fxml \
  -cp "$JAR" -d "$OUT" $(find "$SRC" -name "*.java")