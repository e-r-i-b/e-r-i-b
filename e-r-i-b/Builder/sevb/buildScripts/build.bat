@echo off
if "%STDOUTERR_REDIRECTED%" == "" (
 set STDOUTERR_REDIRECTED=yes
 cmd.exe /c %0 %* 1> "%~dp0\%~n0.out" 2> "%~dp0\%~n0.err"
 exit /b %ERRORLEVEL%
)


set JAVA_HOME=%~dp0\Toolkit\jdk1.5.0_11
set ANT_HOME=%~dp0\Toolkit\apache-ant-1.7.1

set ANT_OPTS=-Xmx512M -XX:MaxPermSize=256m


set PHIZ_DIR="%~dp0\PhizIC"
set CURRENT_CONFIG=sevb

set XML_LIBS=%PHIZ_DIR%\Libraries\XML\xalan.jar;%PHIZ_DIR%\Libraries\XML\serializer.jar;
set PHIZIC_ANT=call "%ANT_HOME%\bin\ant.bat" -lib "%XML_LIBS%" -Dcurrent.config.name=%CURRENT_CONFIG% -f "%PHIZ_DIR%\AntBuilds\PhizIC.ant" 
set CONFIGS_ANT=call "%ANT_HOME%\bin\ant.bat" -lib "%XML_LIBS%" -Dcurrent.config.name=%CURRENT_CONFIG% -f "%PHIZ_DIR%\AntBuilds\configs.ant"

cd "%~dpn0"

echo -----------------start compile external-----------------------
call buildExternal.cmd
echo -----------------end compile external-------------------------



if exist "%~dp0\results" rd /s /q "%~dp0\results"

XCOPY /I /E /Q /H /K /Y "%~dp0api\armour\lib\armour_jni.jar" "%PHIZ_DIR%\Libraries\Armour\"
XCOPY /I /E /Q /H /K /Y "%~dp0api\retail\lib\retail_jni.jar" "%PHIZ_DIR%\Libraries\Retail\"

echo -----------------start compile phizic----------------------
%CONFIGS_ANT% clear
%CONFIGS_ANT% make_current_config 
%PHIZIC_ANT% prepare.image
%PHIZIC_ANT% jar.dictionaries
echo -----------------end compile phizic----------------------

del /F /Q "%PHIZ_DIR%\Libraries\Retail\retail_jni.jar"
del /F /Q "%PHIZ_DIR%\Libraries\Armour\armour_jni.jar"

echo -----------------start copy result-------------------------
XCOPY /I /E /Q /H /K /Y "%~dp0\PhizIC\PhizIC.image" "%~dp0\results\PhizIC.image"
XCOPY /I /E /Q /H /K /Y "%~dp0tools\rsalarm\RS-Alarm\RS-Alarm Setup\RSAlarmSetup.exe" "%~dp0results\RSAlarm\"
XCOPY /I /E /Q /H /K /Y "%~dp0tools\rsbridge\Bin\Release" "%~dp0results\RSBridge\"
echo -----------------end copy result-------------------------