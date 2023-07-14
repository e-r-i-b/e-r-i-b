/*
**  Перед запуском, выставить значения переменных: суффикс для совпавших логинов сотрудников, 
**  количество конвертирумых клиентов, задержку между конвертациями.
**  Также необходимо завести (вручную) подразделение, к которому будут привязываться клиенты
**  После импорта сотрудников небходимо вручную проставить им доступ к подразделениям
**  Процедуры для миграции должны быть созданы в той схеме, в которой работает банк (не под system)
*/
DECLARE
    -- параметры для настройки конвертора 
    s_suffix VARCHAR2(5) := 'BB';            -- суффикс для логина сотрудников, при совпадении  
    schemeEmployer Number :=21;              -- схема доступа сотрудника имеющего права на блокировк/разблокировку клиентов (ID схемы) 
    schemeClient Number :=177125;            -- схема доступа для клиента (ID схемы)
    convertDelay Number :=0.1;               -- задержка между конвертациями (в секундах) 
    countForConverting Number :=10;          -- по сколько клиентов конвертировать (партия)
    adapter VARCHAR2(64) := 'cod-bajkal';    -- адаптер (наименование)
	regionID Number:=1;                      -- регион в профиле клиента (для привязки услуг)
	defaultDepartment Number:=10449;         -- подразделение, к которому привязываем сотрудника если не нашли подходящее по ТБ, ОСБ и ВСП (ID)
	billingID Number:=21;                    -- ID билинговой системы для платежей (id IQW)		
BEGIN
    -- Настраиваем конвертер
    UPDATE CONVERTER_CONFIG 
        SET "suffix"=s_suffix, "scheme_Employer"=schemeEmployer, "scheme_Client"=schemeClient, "convert_Delay"=convertDelay, "count_For_Converting"=countForConverting, "adapter"=adapter, "region_id"=regionID, "default_Department"=defaultDepartment, "billing_id"=billingID, "stop"='0'
            WHERE "id" = '1';

    -- конвертируем сотрудников 1-всех; 0-только выбранных
     --converter.MigrateEmployees('1');
    -- конвертируем клиентов 1-всех; 0-только выбранных
    converter.MigrateClients('0'); 
    -- удаление клиентов
    -- DeleteClients;
    -- удаление сотрудников
     --converter.DeleteEmployees;

    -- сохранение паролей для сконвертированных сотрудников в файл
	/*  выбираем сохранение результатов запроса в файл
		select 'Логин:' as TLOGIN, CE.LOGIN, 'Пароль:' as TPASSWORD, CE."PASSWORD", 'Сотрудник:' as TEMPLOYEE,  EM.USR_NAME,' Подразделение: '||EM.TB||' '||EM.OSB||' '||EM.OFFICE
			from CONVERTER_EMPLOYEES CE
				left join V_EMPLOYEES@psiLink EM on EM.ID=CE.ID_SBOL
					where STATE = 'O'
	*/
END;