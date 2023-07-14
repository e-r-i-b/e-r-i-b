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

:: Путь к выполняемому sql - скрипту
set SCRIPT_HOME="D:\work\v118\Additional\sbrf\push\scripts"

:: Куда сохранять файлы
SET OUTPUT_PATH="D:\work\v118\Additional\sbrf\push\scripts"

:: С какого числа формировать отчет. Указать дату в формате dd.mm.yyyy. Если дата не указана, выборка будет осуществляться за предыдущий день. Параметр не удалять!
set START_DATE_VALUE=""

:: До какого числа формировать отчет. Указать дату в формате dd.mm.yyyy. Если дата не указана, выборка будет осуществляться за предыдущий день. Параметр не удалять!
set END_DATE_VALUE=""

:: номер отчета
set REPORT_VALUE="1"

:: Символ-разделитель для CSV-файла с результатом выполненения скрипта
set SEPARATOR_CHARACTER=";"

chcp 1251

%SQLPLUS_HOME%\sqlplus %SCHEME_NAME%/%SCHEME_PASS%@//%SERVER_NAME%:%SERVER_PORT%/%DB% @%SCRIPT_HOME%\queryForBat%REPORT_VALUE%.sql %OUTPUT_PATH% %START_DATE_VALUE% %END_DATE_VALUE% %SEPARATOR_CHARACTER%