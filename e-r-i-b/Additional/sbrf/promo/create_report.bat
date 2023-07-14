:: Путь до sqlplus
set SQLPLUS_HOME="C:\oracle\product\10.2.0\db_1\BIN"

:: Имя схемы БД
set SCHEME_NAME="SBRF_118_CSA"

:: Пароль к схеме БД
set SCHEME_PASS="SBRF_118_CSA"

:: Сервер
set SERVER_NAME="actinia"

:: Порт
set SERVER_PORT="1521"

:: Имя БД
set DB="PhizIC"

:: Путь к выполняемому sql - скрипту
set SCRIPT_HOME="C:\Work\v1.18\Additional\sbrf\promo"

:: Куда сохранять файлы
SET OUTPUT_PATH="C:\Work\v1.18\Additional\sbrf\promo"

:: Указать дату в формате dd.mm.yyyy. Если дата не указана, выборка будет осуществляться за текущий месяц. Параметр не удалять!
::Дата начала формирования отчета
set REPORT_BEGIN_DATE="01.12.2012"
::Дата окончания формирования отчета
set REPORT_END_DATE="31.12.2012"

:: Символ-разделитель для CSV-файла с результатом выполненения скрипта
set SEPARATOR_CHARACTER=";"

chcp 1251

%SQLPLUS_HOME%\sqlplus %SCHEME_NAME%/%SCHEME_PASS%@//%SERVER_NAME%:%SERVER_PORT%/%DB% @%SCRIPT_HOME%\queryForBat.sql %OUTPUT_PATH% %REPORT_BEGIN_DATE% %REPORT_END_DATE% %SEPARATOR_CHARACTER%