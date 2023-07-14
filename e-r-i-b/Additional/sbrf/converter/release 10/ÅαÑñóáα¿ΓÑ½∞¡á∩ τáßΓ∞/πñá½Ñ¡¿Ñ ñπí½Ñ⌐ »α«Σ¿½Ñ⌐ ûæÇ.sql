--поиск дублей клиентов
select t.* from ( 
    select count(1) over (partition by 
       upper(trim(regexp_replace(sur_name||' '||first_name||' '||patr_name,'( )+',' '))),
       birthdate,
       tb,
       replace(passport,' ','')
    ) as cnt, c.* from csa_profiles c
) t
where cnt>1
order by t.birthdate, mapi_password_creation_date
/
--удаление дублей
declare
    correct_prof number := 330; --тот, который оставляем
    old_prof     number := 101; --тот, который удаляем
begin
    --перепривязываем коннекторы
    execute immediate 'update csa_connectors t set t.profile_id=:new_prof where t.profile_id=:old_prof' using correct_prof, old_prof;
    --удаляем операции по старому профилю
    execute immediate 'delete from csa_operations t where t.profile_id=:old_prof' using old_prof;
    --удаляем дубликат профиля
    execute immediate 'delete from csa_profiles t where t.id=:old_prof' using old_prof;
end;
