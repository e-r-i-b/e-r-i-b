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
set SCRIPT_HOME="C:\SVN\v118\Additional\sbrf\loyalty"

:: ���� ��������� �����
SET OUTPUT_PATH="C:\SVN\v118\Additional\sbrf\loyalty"

:: �� ����� ����� ����������� �����. ������� ���� � ������� dd.mm.yyyy. ���� ���� �� �������, ������� ����� �������������� �� ���������� ����. �������� �� �������!
set DATE_VALUE=""

:: ������-����������� ��� CSV-����� � ����������� ������������ �������
set SEPARATOR_CHARACTER=";"

chcp 1251

%SQLPLUS_HOME%\sqlplus %SCHEME_NAME%/%SCHEME_PASS%@//%SERVER_NAME%:%SERVER_PORT%/%DB% @%SCRIPT_HOME%\queryForBat.sql %OUTPUT_PATH% %DATE_VALUE% %SEPARATOR_CHARACTER%