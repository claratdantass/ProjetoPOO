#!/bin/bash
JAVAFX_LIB="/caminho/para/javafx-sdk-17.0.15/lib"
JAR="postgresql-42.7.5.jar"
OUT="out"
MAIN="com.catalogogatos.Main"

java --module-path "$JAVAFX_LIB" --add-modules javafx.controls,javafx.fxml \
  -cp "$OUT":"$JAR" "$MAIN"