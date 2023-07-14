--временная таблица для логирования переноса алиасов
create table extractor_csa_login_alias  ( 
    LOGIN   	varchar2(40)    not null,
    ALIAS   	varchar2(40)    not null,
    MDATE       timestamp(6)        null,
    STATUS      varchar2(2)         null,
    STATUS_DESC varchar2(1000)      null
)
/
create index extractor_i_alias on extractor_csa_login_alias(upper(alias), mdate)
/

--Пакет с процедурами для переноса данных из ЦСА СБОЛ ЦА в ЦСА ЕРИБ
create or replace package alias_extractor is

    --извлечение данных из базы ЦСА СБОЛ ЦА и перенос в ЦСА ЕРИБ (создание фиктивных профилей)
    procedure start_extracting;

    --извлечение данных из базы ЦСА СБОЛ ЦА и перенос в ЦСА ЕРИБ по конкретному алиасу (создание фиктивного профиля)
    procedure start_extracting(CSAAlias in varchar2);

end alias_extractor;
/
create or replace package body alias_extractor is

    --Таблица с алиасами и iPas логинами СБОЛ ЦА
    OLD_CSA_ALIAS_TABLE        constant          varchar2(64) := '';

    --генерация рендомного GUID
    function getGUID return varchar2 as language java name 'RandomGUID.getGUID() return java.lang.String';

    --извлечение данных из базы ЦСА СБОЛ ЦА и перенос в ЦСА ЕРИБ (создание фиктивных профилей)
    procedure start_extracting is
        profileId           csa_profiles.id%type;
        connectorGUID       csa_connectors.guid%type;
        connectoriPasLogin  csa_connectors.user_id%type;
        connectorAlias      csa_connectors.login%type;

        type alias_rec is record (
            recLogin varchar2(40),
            recAlias varchar2(40)
        );
        type temp_table_type is table of alias_rec index by binary_integer;
        temp_alias temp_table_type;
        cur_step varchar2(1024);
        OraError varchar2(1024);
    begin
        --Получаем данные из ЦСА СБОЛ ЦА
        execute immediate 'select login, alias from '||OLD_CSA_ALIAS_TABLE bulk collect into temp_alias;

        if (temp_alias.count > 0) then

            for idx in 1..temp_alias.count loop
                begin
                    savepoint start_insert;
                    cur_step:='инициализация: '||temp_alias(idx).recLogin||' '||temp_alias(idx).recAlias;
                    connectorGUID := upper(getGUID);
                    connectoriPasLogin := temp_alias(idx).recLogin;
                    connectorAlias := temp_alias(idx).recAlias;


                    --выбираем ID создаваемого профиля
                    cur_step:='добавляем профиль';
                    select s_csa_profiles.nextval into profileId from dual;

                    --профиль клиента
                    execute immediate 'insert into csa_profiles(id, first_name, sur_name, patr_name, passport, birthdate, tb, mapi_password_value, mapi_password_salt, mapi_password_creation_date)
                        values(:profileId_, ''X'', :iPasLogin_, ''X'', ''00 00 000000'', :birthdate_, ''00'', '''', '''', null)'
                        using profileId, connectoriPasLogin, to_date('01.01.1900 00:00:00', 'dd.mm.yyyy hh24:mi:ss');

                    --коннектор
                    cur_step:='добавляем коннектор';
                    execute immediate 'insert into csa_connectors(id, guid, creation_date, state, type, user_id, login, cb_code, card_number, block_reason, blocked_until, auth_errors, profile_id, device_info, device_state, last_session_id, last_session_date)
                        values(s_csa_connectors.nextval, :GUID_, sysdate, ''ACTIVE'', ''TERMINAL'', :iPasLogin_, :aliasCSA_, ''00000000'', :iPasLogin_, '''', null, 0, :profileId_, '''', '''', '''', null)'
                    using connectorGUID, connectoriPasLogin, connectorAlias, connectoriPasLogin, profileId;

                    --пишем в лог статус
                    cur_step:='логирование результата';
                    execute immediate 'insert into extractor_csa_login_alias( login, alias, mdate, status, status_desc ) values (:connectoriPasLogin_, :connectorAlias_, :MDATE_, ''O'', ''Добавлены данные (''||:param||'')'')'
                        using connectoriPasLogin, connectorAlias, sysdate, connectorGUID||'\'||connectoriPasLogin||'\'||connectorAlias;

                    commit;

                exception
                    when OTHERS then
                        rollback to start_insert;
                        --пишем в лог статус
                        OraError:=SQLERRM;
                        execute immediate 'insert into extractor_csa_login_alias( login, alias, mdate, status, status_desc ) values (:connectoriPasLogin_, :connectorAlias_, :MDATE_, ''E'', ''Не удалось добавить данные по (''||:param||'') возникла ошибка:''||:OraError||'' шаг:''||:cur_step)'
                            using temp_alias(idx).recLogin, temp_alias(idx).recAlias, sysdate, connectorGUID||'\'||connectoriPasLogin||'\'||connectorAlias, OraError, cur_step;
                        commit;
                end;

            end loop;
            commit;

        end if;

    end start_extracting;

    --извлечение данных из базы ЦСА СБОЛ ЦА и перенос в ЦСА ЕРИБ по конкретному алиасу (создание фиктивного профиля)
    procedure start_extracting(CSAAlias in varchar2) is
        profileId           csa_profiles.id%type;
        connectorGUID       csa_connectors.guid%type;
        connectoriPasLogin  csa_connectors.user_id%type;
        connectorAlias      csa_connectors.login%type;

        type alias_rec is record (
            recLogin varchar2(40),
            recAlias varchar2(40)
        );
        type temp_table_type is table of alias_rec index by binary_integer;
        temp_alias temp_table_type;

        cur_step varchar2(1024);
        OraError varchar2(1024);

    begin
        --Получаем данные из ЦСА СБОЛ ЦА
        execute immediate 'select login, alias from '||OLD_CSA_ALIAS_TABLE||' where upper(alias)=:CSAAlias_' bulk collect into temp_alias using upper(CSAAlias);

        if (temp_alias.count > 0) then

            for idx in 1..temp_alias.count loop
                begin
                    savepoint start_insert;
                    cur_step:='инициализация: '||temp_alias(idx).recLogin||' '||temp_alias(idx).recAlias;

                    connectorGUID := upper(getGUID);
                    connectoriPasLogin := temp_alias(idx).recLogin;
                    connectorAlias := temp_alias(idx).recAlias;

                    --выбираем ID создаваемого профиля
                    select s_csa_profiles.nextval into profileId from dual;

                    --профиль клиента
                    cur_step:='добавляем профиль';
                    execute immediate 'insert into csa_profiles(id, first_name, sur_name, patr_name, passport, birthdate, tb, mapi_password_value, mapi_password_salt, mapi_password_creation_date)
                        values(:profileId_, ''X'', :iPasLogin_, ''X'', ''00 00 000000'', :birthdate_, ''00'', '''', '''', null)'
                        using profileId, connectoriPasLogin, to_date('01.01.1900 00:00:00', 'dd.mm.yyyy hh24:mi:ss');

                    --коннектор
                    cur_step:='добавляем коннектор';
                    execute immediate 'insert into csa_connectors(id, guid, creation_date, state, type, user_id, login, cb_code, card_number, block_reason, blocked_until, auth_errors, profile_id, device_info, device_state, last_session_id, last_session_date)
                        values(s_csa_connectors.nextval, :GUID_, sysdate, ''ACTIVE'', ''TERMINAL'', :iPasLogin_, :aliasCSA_, ''00000000'', :iPasLogin_, '''', null, 0, :profileId_, '''', '''', '''', null)'
                    using connectorGUID, connectoriPasLogin, connectorAlias, connectoriPasLogin, profileId;

                    --пишем в лог статус
                    cur_step:='логирование результата';
                    execute immediate 'insert into extractor_csa_login_alias( login, alias, mdate, status, status_desc ) values (:connectoriPasLogin_, :connectorAlias_, :MDATE_, ''O'', ''Добавлены данные (''||:param||'')'')'
                        using connectoriPasLogin, connectorAlias, sysdate, connectorGUID||'\'||connectoriPasLogin||'\'||connectorAlias;

                    commit;

                exception
                    when OTHERS then
                        rollback to start_insert;
                        --пишем в лог статус
                        OraError:=SQLERRM;
                        execute immediate 'insert into extractor_csa_login_alias( login, alias, mdate, status, status_desc ) values (:connectoriPasLogin_, :connectorAlias_, :MDATE_, ''E'', ''Не удалось добавить данные по (''||:param||'') ''||:OraError||'' шаг:''||:cur_step)'
                            using temp_alias(idx).recLogin, temp_alias(idx).recAlias, sysdate, connectorGUID||'\'||connectoriPasLogin||'\'||connectorAlias, OraError, cur_step;
                        commit;
                end;

            end loop;
            commit;

        end if;

    end start_extracting;

end alias_extractor;
/