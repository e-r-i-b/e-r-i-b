:: Имя схемы БД
set SCHEME_NAME="LOGIN"

:: Пароль к схеме БД
set SCHEME_PASS="PASSWORD"

:: Сервер
set SERVER_NAME="10.00.00.01"

:: Порт
set SERVER_PORT="1521"

:: Имя БД
set DB="csa"


SET NLS_LANG=AMERICAN_CIS.CL8MSWIN1251
sqlplus %SCHEME_NAME%/%SCHEME_PASS%@//%SERVER_NAME%:%SERVER_PORT%/%DB% @job.sql 

exit
