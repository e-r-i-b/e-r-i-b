REM Установите пути если они у не установлены в системе
SET JAVA_HOME=C:\Java\jdk1.5.0_11
SET ANT_HOME=C:\Java\apache-ant-1.7.0

set CURRENT_CONFIG=@current.config.name@

if "%JAVA_HOME%" == "" goto ADD_JAVA

if "%ANT_HOME%" == "" goto ADD_ANT

set CALL_ANT=call "%ANT_HOME%\bin\ant.bat" -f
set ANT_OPTS=-Xmx512M -XX:MaxPermSize=256m -Dcurrent.config.name=sbrf
del *.log
%CALL_ANT% configs.ant prepare_last
rename install.log install_1_import.log
%CALL_ANT% import.ant importClients
rename install.log install_2_import.log


GOTO EXIT

:ADD_JAVA

echo JAVA_HOME is not set.
echo Set JAVA_HOME to the directory of your local JDK to avoid this message.

GOTO EXIT

:ADD_ANT
echo ANT_HOME is not set.
echo Set ANT_HOME to the directory of your local APACHE ANT to avoid this message.

GOTO EXIT


:EXIT
