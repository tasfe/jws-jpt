@echo off
if "%OS%" == "Windows_NT" setlocal

call setvars

cd %JWS_HOME%\bin
call ant install