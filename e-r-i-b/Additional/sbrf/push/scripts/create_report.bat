:: ���� �� sqlplus
set SQLPLUS_HOME="C:\oracle\product\10.2.0\db_1\BIN"

:: ��� ����� ��
set SCHEME_NAME="SBRF_118"

:: ������ � ����� ��
set SCHEME_PASS="SBRF_118"

:: ������
set SERVER_NAME="actinia"

:: ����
set SERVER_PORT="1521"

:: ��� ��
set DB="PhizIC"

:: ���� � ������������ sql - �������
set SCRIPT_HOME="D:\work\v118\Additional\sbrf\push\scripts"

:: ���� ��������� �����
SET OUTPUT_PATH="D:\work\v118\Additional\sbrf\push\scripts"

:: � ������ ����� ����������� �����. ������� ���� � ������� dd.mm.yyyy. ���� ���� �� �������, ������� ����� �������������� �� ���������� ����. �������� �� �������!
set START_DATE_VALUE=""

:: �� ������ ����� ����������� �����. ������� ���� � ������� dd.mm.yyyy. ���� ���� �� �������, ������� ����� �������������� �� ���������� ����. �������� �� �������!
set END_DATE_VALUE=""

:: ����� ������
set REPORT_VALUE="1"

:: ������-����������� ��� CSV-����� � ����������� ������������ �������
set SEPARATOR_CHARACTER=";"

chcp 1251

%SQLPLUS_HOME%\sqlplus %SCHEME_NAME%/%SCHEME_PASS%@//%SERVER_NAME%:%SERVER_PORT%/%DB% @%SCRIPT_HOME%\queryForBat%REPORT_VALUE%.sql %OUTPUT_PATH% %START_DATE_VALUE% %END_DATE_VALUE% %SEPARATOR_CHARACTER%