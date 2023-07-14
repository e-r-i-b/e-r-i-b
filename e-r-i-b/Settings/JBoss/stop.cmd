if exist "%~dp0\setenv.cmd" ( call "%~dp0\setenv.cmd" )
call "%JBOSS_HOME%\bin\shutdown.bat" -s jnp://localhost:1099 -S
if exist "%~dp0\after.cmd"  ( call "%~dp0\after.cmd"  )