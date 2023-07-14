-- Номер ревизии: 57478
-- Комментарий: BUG067425: Ошибка при чтении пустых настроек из properties 

--Основная БД:

update PROPERTIES set PROPERTY_KEY='com.rssl.gate.connection.timeout.esb' where CATEGORY = 'esb.gate.properties'
/
update PROPERTIES set PROPERTY_KEY='com.rssl.gate.connection.timeout.ipas' where CATEGORY = 'ipas.gate.properties'
/

-- Настройки шлюзов
declare
    n integer;
begin
    n:=0;
    for i in (select * from PROPERTIES where regexp_like(category,'^[^.]+\.[^.]+\.[^.]+$') 
    and regexp_substr(category, '^[^.]+') not in ('PhizIC','CSAFront','CSABack')) loop
        begin
            update properties set CATEGORY = regexp_substr(category, '^[^.]+') where id = i.id;
            exception
            when dup_val_on_index then begin
                 n:=n+1;
                update properties set CATEGORY = regexp_substr(category, '^[^.]+') || 'conflict' where id = i.id;
            end;
        end;
    end loop;
    dbms_output.put_line('!!!ВНИМАНИЕ!!! Количество конфликтов: ' || n);
end;
/

-- настройки основного приложения без префикса
declare
    n integer;
begin
    n:=0;
    for i in (select * from PROPERTIES where regexp_like(category,'^[^.]+\.[^.]+$')) loop
        begin
            update properties set CATEGORY = 'phiz' where id = i.id;
            exception
            when dup_val_on_index then begin
                n:=n+1;
                update properties set CATEGORY = 'phiz_conflict' where id = i.id;
            end;
        end;
    end loop;
    dbms_output.put_line('!!!ВНИМАНИЕ!!! Количество конфликтов: ' || n);
end;
/

-- настройки PhizIC
declare
    n integer;
begin
    n:=0;
    for i in (select * from PROPERTIES where CATEGORY like 'PhizIC%') loop
        begin
            update properties set CATEGORY = 'phiz' where id = i.id;
            exception
            when dup_val_on_index then begin
                n:=n+1;
                update properties set CATEGORY = 'phiz_conflict' where id = i.id;
            end;
        end;
    end loop;
    dbms_output.put_line('!!!ВНИМАНИЕ!!! Количество конфликтов: ' || n);
end;
/

-- Настройки CSAFront
declare
    n integer;
begin
    n:=0;
    for i in (select * from PROPERTIES where CATEGORY like 'CSAFront%') loop
        begin
            update properties set CATEGORY = 'phiz-csa' where id = i.id;
            exception
            when dup_val_on_index then begin
                n:=n+1;
                update properties set CATEGORY = 'phiz-csa_conflict' where id = i.id;
            end;
        end;
    end loop;
        dbms_output.put_line('!!!ВНИМАНИЕ!!! Количество конфликтов: ' || n);
end;
/

-- Настройки CSABack
declare
    n integer;
begin
    n:=0;
    for i in (select * from PROPERTIES where CATEGORY like 'CSABack%') loop
        begin
            update properties set CATEGORY = 'csa-back' where id = i.id;
            exception
            when dup_val_on_index then begin
                n:=n+1;
                update properties set CATEGORY = 'phiz-csa_conflict' where id = i.id;
            end;
        end;
    end loop;
        dbms_output.put_line('!!!ВНИМАНИЕ!!! Количество конфликтов: ' || n);
end;

-- Оставшиеся настройки
update properties set category = 'phiz' where category in (
'adapters-config',
'none_property_file',
'SYSTEM',
'esb',
'ipas');
/

--!!!БД ЦСА!!!
-- Настройки CSAFront
declare
    n integer;
begin
    n:=0;
    for i in (select * from PROPERTIES where CATEGORY like 'CSAFront%') loop
        begin
            update properties set CATEGORY = 'phiz-csa' where id = i.id;
            exception
            when dup_val_on_index then begin
                n:=n+1;
                update properties set CATEGORY = 'phiz-csa_conflict' where id = i.id;
            end;
        end;
    end loop;
        dbms_output.put_line('!!!ВНИМАНИЕ!!! Количество конфликтов: ' || n);
end;

-- Настройки CSABack
declare
    n integer;
begin
    n:=0;
    for i in (select * from PROPERTIES where CATEGORY like 'CSABack%') loop
        begin
            update properties set CATEGORY = 'csa-back' where id = i.id;
            exception
            when dup_val_on_index then begin
                n:=n+1;
                update properties set CATEGORY = 'csa-back_conflict' where id = i.id;
            end;
        end;
    end loop;
        dbms_output.put_line('!!!ВНИМАНИЕ!!! Количество конфликтов: ' || n);
end;
/

delete from PROPERTIES where PROPERTY_VALUE is null
/
alter table PROPERTIES modify PROPERTY_VALUE not null
/

-- Номер ревизии: 56993
-- Комментарий: Реализовать хранение справочников ПФП в базе ЦСА Админ
delete from PROPERTIES 
	where CATEGORY = 'pfpDictionary.properties' and INSTR(PROPERTY_KEY, 'com.rssl.pfp.settings.recommendation.cards.') > 0
/
-- Номер ревизии: 57693
-- Комментарий: ENH067611: АЛФ. Увеличить количество категорий, которые может создавать клиент
update PROPERTIES set PROPERTY_VALUE='20' where PROPERTY_KEY='com.rssl.iccs.settings.alf.categories.count' and CATEGORY='phiz'
/

