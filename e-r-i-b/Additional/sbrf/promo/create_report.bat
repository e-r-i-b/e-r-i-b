:: ���� �� sqlplus
set SQLPLUS_HOME="C:\oracle\product\10.2.0\db_1\BIN"

:: ��� ����� ��
set SCHEME_NAME="SBRF_118_CSA"

:: ������ � ����� ��
set SCHEME_PASS="SBRF_118_CSA"

:: ������
set SERVER_NAME="actinia"

:: ����
set SERVER_PORT="1521"

:: ��� ��
set DB="PhizIC"

:: ���� � ������������ sql - �������
set SCRIPT_HOME="C:\Work\v1.18\Additional\sbrf\promo"

:: ���� ��������� �����
SET OUTPUT_PATH="C:\Work\v1.18\Additional\sbrf\promo"

:: ������� ���� � ������� dd.mm.yyyy. ���� ���� �� �������, ������� ����� �������������� �� ������� �����. �������� �� �������!
::���� ������ ������������ ������
set REPORT_BEGIN_DATE="01.12.2012"
::���� ��������� ������������ ������
set REPORT_END_DATE="31.12.2012"

:: ������-����������� ��� CSV-����� � ����������� ������������ �������
set SEPARATOR_CHARACTER=";"

chcp 1251

%SQLPLUS_HOME%\sqlplus %SCHEME_NAME%/%SCHEME_PASS%@//%SERVER_NAME%:%SERVER_PORT%/%DB% @%SCRIPT_HOME%\queryForBat.sql %OUTPUT_PATH% %REPORT_BEGIN_DATE% %REPORT_END_DATE% %SEPARATOR_CHARACTER%