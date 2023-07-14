Конвертер предназанчен для приведения структуры и содержания БД с верси 1.16 к 1.17 (по состоянию на 04.02.2010)
Порядок действий:
1. Настроить систему на БД версии 1.17
2. Положить файл converter-v1_16.sql в папку AntBuilds\db-data
3. Положить файлы converter.ant  и convert.bat в папку AntBuilds.
4. Настроить в файле convert.bat пути до ANT и JDK.
5. Запустить конвертацию, выполнив файл convert.bat
6. Выполнить ант таски:
   _Load2_Operations с опцией удаления неизвестных операций; !!!
   _Load3_Dictionaries;
   _Load4_PaymentForms;
   _Load5_DepositGlobal;
   _Load6_Distributions;
   _Load7_ErrorMessages;
   _Load8_Skins;

После работы конвертера появится файл логов convert.log, в нем содержится лог конвертиации БД (изменения структуры и приведения данных).

* платежи по городу, которые нужно удалить из таблиц BUSINESS_DOCUMENTS, DOCUMENT_EXTENDED_FIELDS идентифицируются
в поле NAME значением appointment, в поле VALUE значением gorod таблицы DOCUMENT_EXTENDED_FIELDS. 

