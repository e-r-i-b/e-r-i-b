:: Путь до sqlplus
set SQLPLUS_HOME="c:\ORACLE\product\10.2.0\db_1\BIN"

:: Название схемы БД
set SCHEME_NAME="SBRF_118"

:: Пароль к схеме БД
set SCHEME_PASS="SBRF_118"

:: Сервер
set SERVER_NAME="actinia"

:: Порт
set SERVER_PORT="1521"

:: Имя БД
set DB="PhizIC11"

:: Путь к выполняемому sql - скрипту
set SCRIPT_HOME="c:\Work\v.1.18\Additional\sbrf\account_opening\opening_and_closing_report"

:: Куда сохранять файл отчета
SET OUTPUT_PATH="c:\Work\v.1.18\Additional\sbrf\account_opening\opening_and_closing_report"

:: Указать дату в формате ДД.ММ.ГГГГ. Параметр не удалять!
::Дата начала формирования отчета (включительно)
set REPORT_BEGIN_DATE="01.10.2013"
::Дата окончания формирования отчета (НЕ включительнно)
set REPORT_END_DATE="01.11.2013"

:: Символ-разделитель для CSV-файла с результатом выполненения скрипта
set SEPARATOR_CHARACTER=";"

::Тербанк
set TB="40"
::ВСП
set VSP="71"

::Название файла отчета
set REPORT_FILE_NAME="report"

chcp 1251

%SQLPLUS_HOME%\sqlplus %SCHEME_NAME%/%SCHEME_PASS%@//%SERVER_NAME%:%SERVER_PORT%/%DB% @%SCRIPT_HOME%\queryForBat.sql %OUTPUT_PATH% %REPORT_BEGIN_DATE% %REPORT_END_DATE% %SEPARATOR_CHARACTER% %TB% %VSP% %REPORT_FILE_NAME%