cd ..
cd ..
cd ..
cd ..
set JWS_HOME=%cd%
set TOOLS_DIR=%JWS_HOME%\tools

set JAVA_HOME=%TOOLS_DIR%\jdk
set ECLIPSE_HOME=%TOOLS_DIR%\eclipse
set M2_HOME=%TOOLS_DIR%\m2
set ANT_HOME=%TOOLS_DIR%\ant
set CATALINA_HOME=%TOOLS_DIR%\tomcat
set PATH=%M2_HOME%\bin;%ANT_HOME%\bin;%PATH%

cd %JWS_HOME%\workspace\archetypes\jpt-basic