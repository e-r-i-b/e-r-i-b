/*
** генератор скриптов скриптов на выдачу прав и сбор статистики
*/
with new_objects as (
  select 
    owner, object_name, object_type 
  from dba_objects 
  where 
      owner='SRB_IKFL' 
  and last_ddl_time > sysdate - 1 
  and object_name not like 'SYS_%'
)
select 
  owner, 
  table_name, 
  partitioned,
  case when partitioned='NO' then 'begin dbms_stats.gather_table_stats( ownname => '''||owner||''', tabname => '''||table_name||''', degree => 32, cascade => true); end;' else null end as statistics_,
  'grant select on '||owner||'.'||table_name||' to OSDBO_USER;' grants_
from dba_tables 
where (owner, table_name) in (
  select  
    owner,
    case
      when t.object_type='INDEX' then ( select distinct table_name from dba_indexes where index_name=t.object_name and owner=t.owner) 
      else t.object_name
    end as table_name_
  from (
    select 
      owner, object_name, object_type native_type,
      case 
        when regexp_like(object_type, '^TABLE.{0,}$') then 'TABLE' 
        when regexp_like(object_type, '^INDEX.{0,}$') then 'INDEX'
        else '?'
      end as object_type    
    from new_objects t    
  ) t
  where object_type in ('TABLE', 'INDEX')
)
order by 2;

