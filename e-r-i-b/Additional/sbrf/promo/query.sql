select nvl(to_date(:report_end_date,'dd.mm.yyyy hh24:mi:ss'), last_day(sysdate)) REPORT_DATE,
       PROMO_ID, to_char(ON_TIME,'dd.mm.yyyy hh24:mi:ss') ON_TIME, to_char(OFF_TIME,'dd.mm.yyyy hh24:mi:ss') OFF_TIME, 
       AUTO_DISCONNECT, sum(CLIENTS_PRM) CLIENTS_PRM, sum(CLIENTS_wakeup) CLIENTS_wakeup, sum(CLIENTS_NPRM) CLIENTS_NPRM,
       pr.TB, pr.OSB, pr.VSP, PROMO_ID PROMO_LASTNAME
  from (select PROMO_ID, ON_TIME, OFF_TIME, AUTO_DISCONNECT, 
               case when sum(CLIENTS_PRM) > 0 then 1 else 0 end CLIENTS_PRM, 
               case when sum(CLIENTS_wakeup) > 0 then 1 else 0 end CLIENTS_wakeup,
               case when sum(CLIENTS_NPRM) > 0 then 1 else 0 end CLIENTS_NPRM,
               TB, OSB, VSP, profile_id
      from (select PROMO_ID, ON_TIME, OFF_TIME, AUTO_DISCONNECT, 
             /*�����, ������������ ����������� ������*/
             case when pcl.before_creation_date is null and pcl.id is not null then 1 else 0 end CLIENTS_PRM,
             /*"����������� ������ (� ������� ���������� ��������� ����� ������ >= 90 ����)"*/
             case when ON_TIME-90 >= pcl.before_creation_date then 1 else 0 end CLIENTS_wakeup,
             /*��� ������� �������� �� ����� ����������*/
             case when pcl.id is not null then 1 else 0 end CLIENTS_NPRM,
             TB, OSB, VSP, pcl.profile_id
          from (select PROMO_ID, cast(ON_TIME as date) ON_TIME, cast(decode(AUTO_DISCONNECT, 1, ON_TIME+1/24, real_close_date) as date) OFF_TIME,
                 AUTO_DISCONNECT, promoret_session_id, tb, osb, office vsp
              from (--����� �����������, ���������� � �������� ������
                select prs.promoter PROMO_ID,
                     prs.creation_date ON_TIME,
                     prs.close_date real_close_date,
                     case when prs.creation_date + 1/2 < nvl(prs.close_date, systimestamp) then 1 else 0 end AUTO_DISCONNECT,
                     prs.session_id promoret_session_id,
                     prs.tb, prs.osb, prs.office
                  from csa_promoter_sessions prs
                 where nvl(to_date(:report_begin_date,'dd.mm.yyyy hh24:mi:ss'),trunc(sysdate,'mm')) <= 
                       nvl(case when prs.creation_date + 1/2 < nvl(prs.close_date, systimestamp) 
                                then prs.creation_date + 1/2 else prs.close_date end, sysdate)/*���� > 12 �����, �� ������������ 1 �����*/
                   and nvl(to_date(:report_end_date,'dd.mm.yyyy hh24:mi:ss'), last_day(sysdate)) >= prs.creation_date
                )
             )
          left join csa_promoclient_log pcl on pcl.promo_session_id = promoret_session_id
                           and pcl.creation_date between greatest(ON_TIME,nvl(to_date(:report_begin_date,'dd.mm.yyyy hh24:mi:ss'),trunc(sysdate,'mm'))) 
                                       and nvl(least(OFF_TIME,nvl(to_date(:report_end_date,'dd.mm.yyyy hh24:mi:ss'), last_day(sysdate))), sysdate)
         )
    group by PROMO_ID, ON_TIME, OFF_TIME, AUTO_DISCONNECT, TB, OSB, VSP, profile_id
     ) pr
group by PROMO_ID, ON_TIME, OFF_TIME, AUTO_DISCONNECT, TB, OSB, VSP
order by PROMO_ID, ON_TIME;