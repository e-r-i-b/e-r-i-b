if exist "%~dp0\setenv.cmd" ( call "%~dp0\setenv.cmd" )
call "%ORACLE_HOME%\bin\oc4j.cmd" -shutdown -port 23791 -password %OC4J_PASSWORD% force
if exist "%~dp0\after.cmd"  ( call "%~dp0\after.cmd"  )