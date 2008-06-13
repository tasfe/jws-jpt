@echo off
if "%OS%" == "Windows_NT" setlocal

call setvars

start mvn jpt.plugins:jpt-generator-plugin:custom-generate -Dexcludes=lib/**