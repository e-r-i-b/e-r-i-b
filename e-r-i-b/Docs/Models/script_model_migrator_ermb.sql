-- Номер ревизии: 62313
-- Номер версии: 1.18
-- Комментарий: Управление кешированием счетчиков
create or replace procedure create_sequence(sequenceName VARCHAR, minval INTEGER, maxval INTEGER, sequenceType VARCHAR default NULL) is
begin
    case
        -- пересоздаваемые каждый день
        when sequenceType = 'EXTENDED'
        then execute immediate 'create sequence '|| sequenceName || ' minvalue ' || minval || ' maxvalue ' || maxval || ' cycle cache 2000';

        -- строго последовательные
        when sequenceType = 'STRICT'
        then execute immediate 'create sequence '|| sequenceName || ' minvalue ' || minval || ' maxvalue ' || maxval || ' cycle nocache order';

        -- сиквенс по умолчанию
        else execute immediate 'create sequence '|| sequenceName || ' minvalue ' || minval || ' maxvalue ' || maxval || ' cycle';
   end case;
end;
go

-- Номер ревизии: 62724
-- Номер версии: 1.18
-- Комментарий: Управление кешированием счетчиков (откат)

create or replace procedure create_sequence(sequenceName VARCHAR, maxval INTEGER) is
begin
    IF regexp_like(sequenceName,'SC_.+_\d{6}') -- если этот сиквенс - обновляемый
    then    -- ставим ему кэш 2000
        execute immediate 'create sequence '|| sequenceName || ' maxvalue ' || maxval || ' cycle cache 2000';
    else
        execute immediate 'create sequence '|| sequenceName || ' maxvalue ' || maxval || ' cycle';
    end if;
end;

-- Номер ревизии:
-- Номер версии: 1.18
-- Комментарий: БД Мигратора (fix)
ALTER TABLE COD MODIFY EDBO_STATE NULL;

-- Номер ревизии:
-- Номер версии: 1.18
-- Комментарий: БД Мигратора (fix)
ALTER TABLE COD MODIFY BRANCHNO_ZONA NULL;
ALTER TABLE COD MODIFY OFFICE_ZONE NULL;

-- Номер ревизии:
-- Номер версии: 1.18
-- Комментарий:
ALTER TABLE MIGRATION_INFO DROP (PROFILE_CREATED)

-- Номер ревизии:
-- Номер версии: 1.18
-- Комментарий: БД Мигратора (fix)
ALTER TABLE MBK MODIFY LAST_REGISTRATION_DATE NULL;

-- Номер ревизии:
-- Номер версии: 1.18
-- Комментарий: 
DELETE FROM MIGRATION_INFO;

ALTER TABLE MIGRATION_INFO DROP COLUMN MBV_MIGRATION_ID;
ALTER TABLE MIGRATION_INFO ADD MBV_MIGRATION_ID VARCHAR2(32);

-- Номер ревизии:
-- Номер версии: 1.18
-- Комментарий: Изменение длины поля номера договора УДБО
ALTER TABLE COD MODIFY (EDBO_NO VARCHAR2(16));

-- Номер ревизии: 71126
-- Номер версии: 1.18
-- Комментарий: BUG082215  ЕРМБ. Мигрируют клиенты. которые мигрировать не должны. 
ALTER TABLE PHONES MODIFY REGISTRATION_DATE NULL;

-- Номер ревизии: 79134
-- Номер версии: 1.18
-- Комментарий: BUG084000  [ЕРМБ, АС Мигратор] Исключенеи при добавлении конфликтных клиентов в БД миграции. (бд)
ALTER TABLE CONFLICTED_CLIENTS DROP COLUMN LAST_SMS_ACTIVITY
