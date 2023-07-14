
:: Путь до sqlplus
set SQLPLUS_HOME="C:\oracle\product\10.2.0\db_1\BIN"

:: Имя схемы БД
set SCHEME_NAME="SBRF_118"

:: Пароль к схеме БД
set SCHEME_PASS="SBRF_118"

:: Сервер
set SERVER_NAME="actinia"

:: Порт
set SERVER_PORT="1521"

:: Имя БД
set DB="PhizIC"

:: Куда сохранять файлы
SET OUTPUT_PATH="C:\SVN\v118\Additional\sbrf\autopayments"

:: Путь до файла с данными
set file=./script.txt


@Echo Off
(
echo set serveroutput on
echo spool ^&1\result.txt;
echo begin
echo dbms_output.put_line^('Не удалось обновить данные для:'^);
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





