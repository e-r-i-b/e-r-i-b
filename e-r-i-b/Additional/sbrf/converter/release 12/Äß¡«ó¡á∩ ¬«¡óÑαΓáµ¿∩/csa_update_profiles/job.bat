:: ��� ����� ��
set SCHEME_NAME="LOGIN"

:: ������ � ����� ��
set SCHEME_PASS="PASSWORD"

:: ������
set SERVER_NAME="10.00.00.01"

:: ����
set SERVER_PORT="1521"

:: ��� ��
set DB="csa"


SET NLS_LANG=AMERICAN_CIS.CL8MSWIN1251
sqlplus %SCHEME_NAME%/%SCHEME_PASS%@//%SERVER_NAME%:%SERVER_PORT%/%DB% @job.sql 

exit
