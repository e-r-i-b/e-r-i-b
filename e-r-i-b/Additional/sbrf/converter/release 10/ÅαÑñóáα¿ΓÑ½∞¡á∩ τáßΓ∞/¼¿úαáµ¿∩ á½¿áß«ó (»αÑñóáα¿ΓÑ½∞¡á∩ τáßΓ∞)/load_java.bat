:: ��� ����� ��
set SCHEME_NAME="csatest"

:: ������ � ����� ��
set SCHEME_PASS="csatest"

:: ������
set SERVER_NAME="10.10.10.10"

:: ����
set SERVER_PORT="1521"

:: ��� ��
set DB="erib"

SET NLS_LANG=AMERICAN_CIS.CL8MSWIN1251
loadjava -thin -resolve -user %SCHEME_NAME%/%SCHEME_PASS%@//%SERVER_NAME%:%SERVER_PORT%/%DB% RandomGUID.class