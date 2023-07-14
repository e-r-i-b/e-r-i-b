if exist "%~dp0\setenv.cmd" ( call "%~dp0setenv.cmd" )
if exist "%~dp0\before.cmd" ( call "%~dp0before.cmd" )

for  /D %%i IN (%~dp0\..\..\*.ear) DO copy "%%i\META-INF\orion-application.xml" "%ORACLE_HOME%\j2ee\home\application-deployments\%%~ni\"
call "%ORACLE_HOME%\bin\oc4j.cmd" -start