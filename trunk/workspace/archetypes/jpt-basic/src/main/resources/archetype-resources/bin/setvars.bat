cd ..

SET PRJ_HOME=%cd%
SET FLEX_SRC=%PRJ_HOME%\src\main\webapp\server\flex
SET FLEX_TARGET=%PRJ_HOME%\src\main\webapp\scripts\flash

cd ..
cd ..
set JWS_HOME=%cd%
set TOOLS_DIR=%JWS_HOME%\tools

set JAVA_HOME=%TOOLS_DIR%\jdk
set FLEX_HOME=%TOOLS_DIR%\flex
set ECLIPSE_HOME=%TOOLS_DIR%\eclipse
set M2_HOME=%TOOLS_DIR%\m2
set ANT_HOME=%TOOLS_DIR%\ant
set CATALINA_HOME=%TOOLS_DIR%\tomcat
set PATH=%M2_HOME%\bin;%ANT_HOME%\bin;%FLEX_HOME%\bin;%PATH%

cd %PRJ_HOME%