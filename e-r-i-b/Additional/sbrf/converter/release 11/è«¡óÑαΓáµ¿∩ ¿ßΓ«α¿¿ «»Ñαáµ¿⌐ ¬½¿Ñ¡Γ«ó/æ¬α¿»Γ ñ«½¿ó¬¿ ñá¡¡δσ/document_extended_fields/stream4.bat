:: ��� ����� ��
set SCHEME_NAME=""

:: ������ � ����� ��
set SCHEME_PASS=""

:: ������
set SERVER_NAME=""

:: ����
set SERVER_PORT=""

:: ��� ��
set DB=""


SET NLS_LANG=AMERICAN_CIS.CL8MSWIN1251
sqlplus %SCHEME_NAME%/%SCHEME_PASS%@//%SERVER_NAME%:%SERVER_PORT%/%DB% @Reports.sql 3

exit