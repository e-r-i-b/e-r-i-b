if exist "%~dp0\setenv.cmd" ( call "%~dp0\setenv.cmd" )
if exist "%~dp0\before.cmd" ( call "%~dp0\before.cmd" )
copy "%~dp0\..\..\Exploded.ear\META-INF\phizic-ds.xml" "%JBOSS_HOME%\server\phizic\deploy\"
call "%JBOSS_HOME%\bin\run.bat" -c phizic