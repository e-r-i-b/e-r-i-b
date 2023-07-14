set pages 0 feed off
set trimspool on
set term off
set verify off
set linesize 200

spool off

/*Даты начала и окончания отчетного периода*/
column report_begin_date new_value rep_begin_date
column report_end_date new_value rep_end_date
select nvl('&2', to_char(trunc(sysdate,'mm'), 'DD.MM.YYYY')) as report_begin_date from dual;
select nvl('&3', to_char(last_day(sysdate), 'DD.MM.YYYY')) as report_end_date from dual;

spool &1\report-&rep_end_date..csv

select replace('REPORT_DATE,PROMO_ID,ON_TIME,OFF_TIME,AUTO_DISCONNECT,CLIENTS_PRM,CLIENTS_wakeup,CLIENTS_NPRM,TB,OSB,VSP,PROMO_LASTNAME',',',nvl('&4',','))
  from dual
union all
select *
  from (select replace(REPORT_DATE||','||PROMO_ID||','||to_char(ON_TIME,'dd.mm.yyyy hh24:mi:ss')||','||to_char(OFF_TIME,'dd.mm.yyyy hh24:mi:ss')||
                       ','||AUTO_DISCONNECT||','||CLIENTS_PRM||','||CLIENTS_wakeup||','||CLIENTS_NPRM||','||pr.TB||','||pr.OSB||','||pr.VSP||','||PROMO_ID,',',nvl('&4',','))
          from (select '&rep_end_date' REPORT_DATE,
                       PROMO_ID, ON_TIME, OFF_TIME, AUTO_DISCONNECT, 
					   sum(CLIENTS_PRM) CLIENTS_PRM, sum(CLIENTS_wakeup) CLIENTS_wakeup, sum(CLIENTS_NPRM) CLIENTS_NPRM,
                       pr.TB, pr.OSB, pr.VSP, PROMO_ID PROMO_LASTNAME
                  from (select PROMO_ID, ON_TIME, OFF_TIME, AUTO_DISCONNECT, 
                               case when sum(CLIENTS_PRM) > 0 then 1 else 0 end CLIENTS_PRM, 
                               case when sum(CLIENTS_wakeup) > 0 then 1 else 0 end CLIENTS_wakeup,
                               case when sum(CLIENTS_NPRM) > 0 then 1 else 0 end CLIENTS_NPRM,
                               TB, OSB, VSP, profile_id
                          from (select PROMO_ID, ON_TIME, OFF_TIME, AUTO_DISCONNECT, 
                                 /*Новый, привлеченный промоутером клиент*/
                                 case when pcl.before_creation_date is null and pcl.id is not null then 1 else 0 end CLIENTS_PRM,
                                 /*"Разбуженный клиент (с момента последнего успешного входа прошло >= 90 дней)"*/
                                 case when ON_TIME-90 >= pcl.before_creation_date then 1 else 0 end CLIENTS_wakeup,
                                 /*Все клиенты вошедшие за смену промоутера*/
                                 case when pcl.id is not null then 1 else 0 end CLIENTS_NPRM,
                                 TB, OSB, VSP, pcl.profile_id
                              from (select PROMO_ID, cast(ON_TIME as date) ON_TIME, cast(decode(AUTO_DISCONNECT, 1, ON_TIME+1/24, real_close_date) as date) OFF_TIME,
                                     AUTO_DISCONNECT, promoret_session_id, tb, osb, office vsp
                                  from (--смены промоутеров, попадающие в отчетный период
                                        select prs.promoter PROMO_ID,
                                             prs.creation_date ON_TIME,
                                             prs.close_date real_close_date,
                                             case when prs.creation_date + 1/2 < nvl(prs.close_date, systimestamp) then 1 else 0 end AUTO_DISCONNECT,
                                             prs.session_id promoret_session_id,
                                             prs.tb, prs.osb, prs.office
                                          from csa_promoter_sessions prs
                                         where to_date('&rep_begin_date','dd.mm.yyyy hh24:mi:ss') <= 
                                               nvl(case when prs.creation_date + 1/2 < nvl(prs.close_date, systimestamp) 
                                                        then prs.creation_date + 1/2 else prs.close_date end, sysdate)/*если > 12 часов, то ограничиваем 1 часом*/
                                           and to_date('&rep_end_date','dd.mm.yyyy hh24:mi:ss') >= prs.creation_date
                                       )
                                   )
                              left join csa_promoclient_log pcl on pcl.promo_session_id = promoret_session_id
                                               and pcl.creation_date between greatest(ON_TIME,to_date('&rep_begin_date','dd.mm.yyyy hh24:mi:ss')) 
                                                           and nvl(least(OFF_TIME,to_date('&rep_end_date','dd.mm.yyyy hh24:mi:ss')), sysdate)
                             )
                        group by PROMO_ID, ON_TIME, OFF_TIME, AUTO_DISCONNECT, TB, OSB, VSP, profile_id
                       ) pr
                group by PROMO_ID, ON_TIME, OFF_TIME, AUTO_DISCONNECT, TB, OSB, VSP
                order by PROMO_ID, ON_TIME) pr
      );
	  
spool off;
exit;