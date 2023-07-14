:: Имя схемы БД
set SCHEME_NAME="csatest"

:: Пароль к схеме БД
set SCHEME_PASS="csatest"

:: Сервер
set SERVER_NAME="10.10.10.10"

:: Порт
set SERVER_PORT="1521"

:: Имя БД
set DB="erib"

SET NLS_LANG=AMERICAN_CIS.CL8MSWIN1251
loadjava -thin -resolve -user %SCHEME_NAME%/%SCHEME_PASS%@//%SERVER_NAME%:%SERVER_PORT%/%DB% RandomGUID.class