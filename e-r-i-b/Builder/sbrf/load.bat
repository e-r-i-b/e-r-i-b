@echo on
if "%STDOUTERR_REDIRECTED%" == "" (
 set STDOUTERR_REDIRECTED=yes
 cmd.exe /c %0 %* 1> "%~dp0\%~n0.out" 2> "%~dp0\%~n0.err"
 exit /b %ERRORLEVEL%
)

if "%BASE_DIR%" == "" (
	set BASE_DIR=D:\ikfl\auto\sbrf_1.18
)

set TOOLKIT_SOURCE=\\Murena\SCC_Toolkit_PhizIC
rem --------------------------start copy toolkit ------------------------

@echo Start copy toolkit

XCOPY /I /E /Q /H /K /Y "%TOOLKIT_SOURCE%" "%BASE_DIR%\stend\Toolkit"

@echo End copy toolkit

rem --------------------------end copy toolkit ------------------------


set JAVA_HOME=%BASE_DIR%\stend\Toolkit\jdk1.5.0_11
set ANT_HOME=%BASE_DIR%\stend\Toolkit\apache-ant-1.7.1
set ANT_LIB_DIR=%BASE_DIR%\stend\Toolkit\Libraries\ForAnt\
set CURRENT_CONFIG=sbrf


set ANT_OPTS=-Xmx512M -XX:MaxPermSize=512m

set LOAD_ANT=call "%ANT_HOME%\bin\ant.bat" -lib %ANT_LIB_DIR% -f "%~dp0load.ant" -Dload.base.dir=%BASE_DIR%\stend -Dcurrent.config.name=%CURRENT_CONFIG%

cd "%~dp0"

rem --------------------------start load ------------------------

%LOAD_ANT% loadAll

rem --------------------------end load ------------------------

