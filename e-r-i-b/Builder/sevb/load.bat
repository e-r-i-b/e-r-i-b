@echo off
if "%STDOUTERR_REDIRECTED%" == "" (
 set STDOUTERR_REDIRECTED=yes
 cmd.exe /c %0 %* 1> "%~dp0\%~n0.out" 2> "%~dp0\%~n0.err"
 exit /b %ERRORLEVEL%
)

rem Перед запуском!!!!
rem указать 
rem JAVA_HOME - путь к jdk, например: set JAVA_HOME=C:\Java\jdk1.5.0_11
rem ANT_HOME - путь к ant, например: set ANT_HOME=C:\Java\apache-ant-1.7.0


set JAVA_HOME=C:\Java\jdk1.5.0_11
set ANT_HOME=C:\Java\apache-ant-1.7.1
set ANT_LIB_DIR=%~dp0..\..\Libraries\ForAnt\
set BASE_DIR=C:\stend
set CURRENT_CONFIG=sevb
set TOOLKIT_SOURCE=\\SHARK\SCC_Toolkit_PhizIC


set ANT_OPTS=-Xmx512M -XX:MaxPermSize=256m

set LOAD_ANT=call "%ANT_HOME%\bin\ant.bat" -lib %ANT_LIB_DIR% -f "%~dp0load.ant" -Dload.base.dir=%BASE_DIR% -Dcurrent.config.name=%CURRENT_CONFIG%

cd "%~dp0"

rem --------------------------start load ------------------------

%LOAD_ANT% loadAll

rem --------------------------end load ------------------------

rem --------------------------start copy toolkit ------------------------

@echo Start copy toolkit

XCOPY /I /E /Q /H /K /Y "%TOOLKIT_SOURCE%" "%BASE_DIR%\Toolkit"

@echo End copy toolkit

rem --------------------------end copy toolkit ------------------------