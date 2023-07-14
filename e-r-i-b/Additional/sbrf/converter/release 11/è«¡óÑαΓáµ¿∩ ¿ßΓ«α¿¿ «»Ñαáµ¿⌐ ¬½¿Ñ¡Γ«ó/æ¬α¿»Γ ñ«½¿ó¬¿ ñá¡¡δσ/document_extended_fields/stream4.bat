:: Имя схемы БД
set SCHEME_NAME=""

:: Пароль к схеме БД
set SCHEME_PASS=""

:: Сервер
set SERVER_NAME=""

:: Порт
set SERVER_PORT=""

:: Имя БД
set DB=""


SET NLS_LANG=AMERICAN_CIS.CL8MSWIN1251
sqlplus %SCHEME_NAME%/%SCHEME_PASS%@//%SERVER_NAME%:%SERVER_PORT%/%DB% @Reports.sql 3

exit