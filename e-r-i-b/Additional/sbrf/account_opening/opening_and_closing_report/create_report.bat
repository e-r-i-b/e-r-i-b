:: ���� �� sqlplus
set SQLPLUS_HOME="c:\ORACLE\product\10.2.0\db_1\BIN"

:: �������� ����� ��
set SCHEME_NAME="SBRF_118"

:: ������ � ����� ��
set SCHEME_PASS="SBRF_118"

:: ������
set SERVER_NAME="actinia"

:: ����
set SERVER_PORT="1521"

:: ��� ��
set DB="PhizIC11"

:: ���� � ������������ sql - �������
set SCRIPT_HOME="c:\Work\v.1.18\Additional\sbrf\account_opening\opening_and_closing_report"

:: ���� ��������� ���� ������
SET OUTPUT_PATH="c:\Work\v.1.18\Additional\sbrf\account_opening\opening_and_closing_report"

:: ������� ���� � ������� ��.��.����. �������� �� �������!
::���� ������ ������������ ������ (������������)
set REPORT_BEGIN_DATE="01.10.2013"
::���� ��������� ������������ ������ (�� �������������)
set REPORT_END_DATE="01.11.2013"

:: ������-����������� ��� CSV-����� � ����������� ������������ �������
set SEPARATOR_CHARACTER=";"

::�������
set TB="40"
::���
set VSP="71"

::�������� ����� ������
set REPORT_FILE_NAME="report"

chcp 1251

%SQLPLUS_HOME%\sqlplus %SCHEME_NAME%/%SCHEME_PASS%@//%SERVER_NAME%:%SERVER_PORT%/%DB% @%SCRIPT_HOME%\queryForBat.sql %OUTPUT_PATH% %REPORT_BEGIN_DATE% %REPORT_END_DATE% %SEPARATOR_CHARACTER% %TB% %VSP% %REPORT_FILE_NAME%