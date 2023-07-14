REM Установите пути если они у не установлены в системе
REM SET JAVA_HOME=C:\Program Files\Java\JDK-1-5-0-09
REM SET ANT_HOME=C:\Program Files\Java\Ant-1-6-5

set CURRENT_CONFIG=@current.config.name@
set CURRENT_INSTALLER_SETTINGS_FILE=@installer.settings.file@

if "%JAVA_HOME%" == "" goto ADD_JAVA
if "%ANT_HOME%"  == "" goto ADD_ANT

set CALL_ANT=call "%ANT_HOME%\bin\ant.bat" -f
set ANT_OPTS=-Xmx512M -XX:MaxPermSize=256m -Dload.mbeans=false -Dcurrent.config.name=%CURRENT_CONFIG% -Dinstaller.settings.file=%CURRENT_INSTALLER_SETTINGS_FILE%

del %~dpn0\*.log

%CALL_ANT% %~dp0configs.ant prepare_last
rename %~dp0install.log install_1.log
%CALL_ANT% %~dp0builder.ant make.ear
rename %~dp0install.log install_2.log
%CALL_ANT% %~dp0install.ant CreateDB
rename %~dp0install.log install_3.log
%CALL_ANT% %~dp0install.ant CreateDataBaseCSA
rename %~dp0install.log install_4.log
%CALL_ANT% %~dp0install.ant CreateDataBaseCSAAdmin
rename %~dp0install.log install_5.log
%CALL_ANT% %~dp0install.ant CreateDataBaseOfflineDoc
rename %~dp0install.log install_6.log
%CALL_ANT% %~dp0install.ant CreateDataBasePush
rename %~dp0install.log install_7.log
%CALL_ANT% %~dp0install.ant CreateDataBaseLimits
rename %~dp0install.log install_8.log
%CALL_ANT% %~dp0install.ant CreateDataBaseLog
rename %~dp0install.log install_DB_LOG.log
%CALL_ANT% %~dp0install.ant CreateDataBaseUSCT
rename %~dp0install.log install_DB_USCT.log
%CALL_ANT% %~dp0install.ant -Ddata.name="_Load2_Operations"             	    LoadSpecifiedData
rename %~dp0install.log install_9.log
%CALL_ANT% %~dp0install.ant -Ddata.name="_CreateDefaultAdmin"           	    LoadSpecifiedData
rename %~dp0install.log install_10.log
%CALL_ANT% %~dp0install.ant -Ddata.name="_CreateDefaultAdminCSAAdmin"           LoadSpecifiedData
rename %~dp0install.log install_11.log
%CALL_ANT% %~dp0install.ant -Ddata.name="_Load3_Dictionaries"           	    LoadSpecifiedData
rename %~dp0install.log install_12.log
%CALL_ANT% %~dp0install.ant -Ddata.name="_Load4_PaymentForms"           	    LoadSpecifiedData
rename %~dp0install.log install_13.log
%CALL_ANT% %~dp0install.ant -Ddata.name="_Load11_SystemPaymentServices" 	    LoadSpecifiedData
rename %~dp0install.log install_14.log
%CALL_ANT% %~dp0install.ant -Ddata.name="_Load5_DepositGlobal"          	    LoadSpecifiedData
rename %~dp0install.log install_15.log
%CALL_ANT% %~dp0install.ant -Ddata.name="_Load7_ErrorMessages" 			        LoadSpecifiedData
rename %~dp0install.log install_17.log
%CALL_ANT% %~dp0install.ant -Ddata.name="_Load8_Skins"         			        LoadSpecifiedData
rename %~dp0install.log install_18.log
%CALL_ANT% %~dp0install.ant -Ddata.name="_Load9_Groups"        			        LoadSpecifiedData
rename %~dp0install.log install_19.log
%CALL_ANT% %~dp0install.ant -Ddata.name="_InitCommissions"   			        LoadSpecifiedData
rename %~dp0install.log install_20.log
%CALL_ANT% %~dp0install.ant -Ddata.name="_Load10_LoanGlobal" 			        LoadSpecifiedData
rename %~dp0install.log install_21.log
%CALL_ANT% %~dp0install.ant -Ddata.name="_Load13_LoadTranslateMessagesFormFile" LoadSpecifiedData
rename %~dp0install.log install_22.log
%CALL_ANT% %~dp0install.ant -Ddata.name="_Load14_CBRates"         		        LoadSpecifiedData
rename %~dp0install.log install_23.log
%CALL_ANT% %~dp0install.ant -Ddata.name="_Load15_UpdateReceptionTimes"         	LoadSpecifiedData
rename %~dp0install.log install_24.log
%CALL_ANT% %~dp0install.ant -Ddata.name="_Load18_SMSResources"         	        LoadSpecifiedData
rename %~dp0install.log install_25.log
%CALL_ANT% %~dp0install.ant -Ddata.name="_LoadCSA_SMSResources"         	    LoadSpecifiedData
rename %~dp0install.log install_26.log
%CALL_ANT% %~dp0install.ant -Ddata.name="_Load19_PushResources"         	        LoadSpecifiedData
rename %~dp0install.log install_27.log

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
