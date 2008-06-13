@echo off
if "%OS%" == "Windows_NT" setlocal

call setvars

set EXECUTABLE=%CATALINA_HOME%\bin\catalina.bat

set CMD_LINE_ARGS=
:setArgs
if ""%1""=="""" goto doneSetArgs
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto setArgs
:doneSetArgs

call "%EXECUTABLE%" start %CMD_LINE_ARGS%