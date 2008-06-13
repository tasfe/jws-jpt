@echo off
if "%OS%" == "Windows_NT" setlocal

call setvars
cd %JWS_HOME%\workspace\
start mvn archetype:generate -DarchetypeCatalog=file://%JWS_HOME%/settings/m2/archetype-catalog.xml