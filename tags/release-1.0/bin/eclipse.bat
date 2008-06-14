@echo off
if "%OS%" == "Windows_NT" setlocal

call setvars

set EXECUTABLE=%ECLIPSE_HOME%\eclipse.exe 

set CMD_LINE_ARGS=
:setArgs
if ""%1""=="""" goto doneSetArgs
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto setArgs
:doneSetArgs

cd %ECLIPSE_HOME%
start %EXECUTABLE% -data "%JWS_HOME%\workspace" -vm "%JAVA_HOME%\bin\javaw.exe" %CMD_LINE_ARGS%