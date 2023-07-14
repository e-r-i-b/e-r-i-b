
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

:: ���� ��������� �����
SET OUTPUT_PATH="C:\SVN\v118\Additional\sbrf\autopayments"

:: ���� �� ����� � �������
set file=./script.txt


@Echo Off
(
echo set serveroutput on
echo spool ^&1\result.txt;
echo begin
echo dbms_output.put_line^('�� ������� �������� ������ ���:'^);
For /F "usebackq tokens=1,2,3,4 delims=:,," %%i In ("%file%") Do (
       
        echo update SERVICE_PROVIDER_REGIONS set SHOW_IN_PROMO_BLOCK = '0' where region_id = %%i ;
        if  not "%%j"=="" ( 
                echo update SERVICE_PROVIDER_REGIONS set SHOW_IN_PROMO_BLOCK = '1' where region_id = %%i and SERVICE_PROVIDER_ID =  %%j ;
                echo if SQL%%ROWCOUNT =0 then 
                echo         dbms_output.put_line^('regionId='^|^|'%%i'^|^|' providerId='^|^|'%%j'^);
                echo end if;
        )
        if  not "%%k"=="" ( 
                echo update SERVICE_PROVIDER_REGIONS set SHOW_IN_PROMO_BLOCK = '1' where region_id = %%i and SERVICE_PROVIDER_ID =  %%k ;
                echo if SQL%%ROWCOUNT =0 then
                echo        dbms_output.put_line^('regionId='^|^|'%%i'^|^|' providerId='^|^|'%%k'^);
                echo end if; 
        )
        if  not "%%l"=="" ( 
                echo update SERVICE_PROVIDER_REGIONS set SHOW_IN_PROMO_BLOCK = '1' where region_id = %%i and SERVICE_PROVIDER_ID =  %%l ;
                echo if SQL%%ROWCOUNT =0 then 
                echo         dbms_output.put_line^('regionId='^|^|'%%i'^|^|' providerId='^|^|'%%l'^);
                echo end if;
        )

)
echo end; 
echo /
echo spool off;
echo exit
echo /
)>%OUTPUT_PATH%\update.sql


chcp 1251

%SQLPLUS_HOME%\sqlplus %SCHEME_NAME%/%SCHEME_PASS%@//%SERVER_NAME%:%SERVER_PORT%/%DB% @%OUTPUT_PATH%\update.sql %OUTPUT_PATH%





