@echo off
if "%OS%" == "Windows_NT" setlocal

call setvars

start mvn clean package