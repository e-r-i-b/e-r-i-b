REM Установите пути если они у не установлены в системе
 SET JAVA_HOME=C:\Program Files\Java\jdk1.5.0_11\
 SET ANT_HOME=D:\_work\product\apache-ant-1.7.1\

if "%JAVA_HOME%" == "" goto ADD_JAVA

if "%ANT_HOME%" == "" goto ADD_ANT

set CALL_ANT=call "%ANT_HOME%\bin\ant.bat" -f
set ANT_OPTS=-Xmx512M -XX:MaxPermSize=256m
del *.log

%CALL_ANT%  converter.ant Convert_DB_v1.18_cpfl
rename install.log convert.log

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
