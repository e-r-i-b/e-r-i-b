@echo off

rem 
rem Сборка внешних утилит
rem
rem Перед вызовом должны быть установлены параметры окружения:
rem JAVA_HOME - Путь к корню jdk
rem 
rem Возможна также передача их значений черех командную строку в виде пар <имя> <значение>
rem 

if "%STDOUTERR_REDIRECTED%" == "" (
 set STDOUTERR_REDIRECTED=yes
 cmd.exe /c %0 %* 1> "%~dp0\%~n0.out" 2> "%~dp0\%~n0.err"
 exit /b %ERRORLEVEL%
)
set PATH=%PATH%;%JAVA_HOME%;%JAVA_HOME%\bin;

echo -----------------start compile retailJNI,armourJNI-----------------------
call %~dp0api\build.cmd "%~dp0Toolkit\MS2005"
echo -----------------end compile retailJNI,armourJNI-----------------------

set MS_ROOT_FOLDER=%~dp0Toolkit\MS
set VS6_ROOT_FOLDER=%MS_ROOT_FOLDER%\VC98
set VS6_ENV_INCLUDE=%MS_ROOT_FOLDER%\PSDK\Include;%VS6_ROOT_FOLDER%\Include;%VS6_ROOT_FOLDER%\MFC\INCLUDE;
set VS6_ENV_LIBRARY=%MS_ROOT_FOLDER%\PSDK\Lib;%VS6_ROOT_FOLDER%\LIB

set PATH=%MS_ROOT_FOLDER%\VC98\Bin;%MS_ROOT_FOLDER%\Common\MSDev98\Bin\;%PATH%
set PATH=%MS_ROOT_FOLDER%\PSDK\Bin;%PATH%

echo -----------------start compile RSBridge ----------------------------------
call %~dp0tools\rsbridge\build.cmd
echo -----------------end compile RSBridge ----------------------------------

set IS5_ROOT_FOLDER=%~dp0Toolkit\IS

echo -----------------start compile RSAlarm ----------------------------------
start %~dp0tools\rsalarm\build.cmd 
echo -----------------end compile RSAlarm ------------------------------------