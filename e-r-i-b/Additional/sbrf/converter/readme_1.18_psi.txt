Конвертер предназанчен для приведения структуры и содержания БД с версии 1.18 в банке (ветка v1.18_SBRF_06_08_2010) к разрабатываемой 1.18 (по состоянию на 07.10.2010)
Порядок действий:
1. Настроить систему на БД версии 1.18
2. Положить файл converter-v1_18_psi.sql в папку AntBuilds\db-data
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
   _Load11_SystemPaymentServices;

После работы конвертера появится файл логов convert.log, в нем содержится лог конвертиации БД (изменения структуры и приведения данных).

Замечания
1. (!!!)  Следующие скрипты нужно выполнять под пользователем system. Они не внесены в файл конвертера converter-v1_18_psi.sql. Поэтому их нужно испольнить отдельно (вместо <имя схемы> нужно подставить имя схемы конвертируемой БД).

create or replace view <имя_схемы>.CHILDREN_DEPARTMENT_BY_TB as
SELECT replace(sys_connect_by_path(decode(level, 1, children.PARENT_DEPARTMENT), '~'), '~') AS ROOT,
children.ID
FROM <имя_схемы>.DEPARTMENTS children
CONNECT BY PRIOR children.id = children.parent_department
go
