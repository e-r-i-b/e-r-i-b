:: ��� ����� ��
set SCHEME_NAME="srb_ikfl"

:: ������ � ����� ��
set SCHEME_PASS="loadtest"

:: ������
set SERVER_NAME="10.68.16.53"

:: ����
set SERVER_PORT="1521"

:: ��� ��
set DB="erib"

:: ���-�� �������
set COUNT_TREADS=10
:: ���-�� ������� ��� ������
set COUNT_FOR_CONVERT=114000

SET NLS_LANG=AMERICAN_CIS.CL8MSWIN1251
sqlplus %SCHEME_NAME%/%SCHEME_PASS%@//%SERVER_NAME%:%SERVER_PORT%/%DB% @queryForBat.sql  %COUNT_TREADS% %COUNT_FOR_CONVERT%

exit