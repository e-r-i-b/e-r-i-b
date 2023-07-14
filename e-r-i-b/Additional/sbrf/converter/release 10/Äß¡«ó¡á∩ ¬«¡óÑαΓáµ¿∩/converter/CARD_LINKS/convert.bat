:: Имя схемы БД
set SCHEME_NAME="srb_ikfl"

:: Пароль к схеме БД
set SCHEME_PASS="loadtest"

:: Сервер
set SERVER_NAME="10.68.16.53"

:: Порт
set SERVER_PORT="1521"

:: Имя БД
set DB="erib"

:: Кол-во потоков
set COUNT_TREADS=10
:: Кол-во записей для потока
set COUNT_FOR_CONVERT=114000

SET NLS_LANG=AMERICAN_CIS.CL8MSWIN1251
sqlplus %SCHEME_NAME%/%SCHEME_PASS%@//%SERVER_NAME%:%SERVER_PORT%/%DB% @queryForBat.sql  %COUNT_TREADS% %COUNT_FOR_CONVERT%

exit