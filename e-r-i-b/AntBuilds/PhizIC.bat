@echo off
if "%STDOUTERR_REDIRECTED%" == "" (
 set STDOUTERR_REDIRECTED=yes
 cmd.exe /c %0 %* 1> "%~dpn0\%~n0.out" 2> "%~dpn0\%~n0.err"
 exit /b %ERRORLEVEL%
)

if "%JAVA_HOME%"   == "" goto ADD_JAVA
if "%ANT_HOME%"    == "" goto ADD_ANT
if "%ORACLE_HOME%" == "" goto ADD_ORACLE

set ANT_OPTS=-Xmx256M -XX:MaxPermSize=192M
set PHIZIC_ANT=call "%ANT_HOME%\bin\ant.bat" -f "%~dp0phizic.ant"
set MAIL_LIB=%~dp0lib\activation-1.1.jar;%~dp0lib\mailapi-1.4.jar;%~dp0lib\smtp-1.4.jar;

%PHIZIC_ANT% reload -Dcurrent.config.name=sbrf
%PHIZIC_ANT% test.complete -Dcurrent.config.name=sbrf
%PHIZIC_ANT% prepare.image -Dcurrent.config.name=sbrf
call "%ANT_HOME%\bin\ant.bat" -f "%~dp0configs.ant" SetLastConfig
call "%ANT_HOME%\bin\ant.bat" -f "%~dp0builder.ant" make.ear
move "%~dp0..\PhizIC.image\PhizIC.ear" "%~dpn0\%~n0.ear"

"%ORACLE_HOME%\opmn\bin\opmnctl.exe" restartproc ias-component=OC4J
"%JAVA_HOME%\bin\java.exe" -jar %ORACLE_HOME%\j2ee\home\admin_client.jar deployer:cluster:opmn://whale/home oc4jadmin qwe123 -undeploy PhizIC
"%ORACLE_HOME%\opmn\bin\opmnctl.exe" restartproc ias-component=OC4J
"%JAVA_HOME%\bin\java.exe" -jar %ORACLE_HOME%\j2ee\home\admin_client.jar deployer:cluster:opmn://whale/home oc4jadmin qwe123 -deploy -file "%~dpn0\%~n0.ear" -deploymentName PhizIC -bindAllWebApps

set MAIL_LIB=%~dp0lib\activation-1.1.jar;%~dp0lib\mailapi-1.4.jar;%~dp0lib\smtp-1.4.jar
%PHIZIC_ANT% -lib "%MAIL_LIB%" send.log -Dcurrent.config.name=sbrf -Doc4j.root=%ORACLE_HOME%

GOTO EXIT

:ADD_ORACLE
echo ADD_ORACLE is not set.
echo Set ADD_ORACLE to the directory of your local OC4J to avoid this message.

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
