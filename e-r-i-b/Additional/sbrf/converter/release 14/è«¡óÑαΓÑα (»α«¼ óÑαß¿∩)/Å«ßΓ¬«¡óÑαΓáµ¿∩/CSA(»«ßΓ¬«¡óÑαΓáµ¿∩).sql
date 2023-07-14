create table CSA_IKFL.FORDEL$RELEASE14 as 
select FIO, BIRTHDAY, DUL, TB from (
  select 
    upper(trim( regexp_replace (u."SUR_NAME"||' '||u."FIRST_NAME"||' '||u."PATR_NAME",'( )+',' '))) as FIO,
    u.BIRTHDAY, 
    replace("DOC_SERIES"||"DOC_NUMBER",' ','') as DUL,
    dep.TB    
  from USERS@BLOCK1LINK u 
  inner join DOCUMENTS@BLOCK1LINK d on d.PERSON_ID=u.ID
  inner join DEPARTMENTS@BLOCK1LINK dep on dep.ID=u.DEPARTMENT_ID
  where CREATION_TYPE='POTENTIAL'
)
group by FIO, BIRTHDAY, DUL, TB
/

insert into CSA_IKFL.FORDEL$RELEASE14 
select FIO, BIRTHDAY, DUL, TB from (
  select 
    upper(trim( regexp_replace (u."SUR_NAME"||' '||u."FIRST_NAME"||' '||u."PATR_NAME",'( )+',' '))) as FIO,
    u.BIRTHDAY, 
    replace("DOC_SERIES"||"DOC_NUMBER",' ','') as DUL,
    dep.TB    
  from USERS@BLOCK2LINK u 
  inner join DOCUMENTS@BLOCK2LINK d on d.PERSON_ID=u.ID
  inner join DEPARTMENTS@BLOCK2LINK dep on dep.ID=u.DEPARTMENT_ID
  where CREATION_TYPE='POTENTIAL'
)
group by FIO, BIRTHDAY, DUL, TB
/
create unique index CSA_IKFL."UIDX_FORDEL$RELEASE14" on CSA_IKFL."FORDEL$RELEASE14" (
  FIO, 
  BIRTHDAY, 
  DUL, 
  TB
)
/

merge /*+use_nl(cp, t) index(cp, CSA_PROFILES_UNIVERSAL_ID)*/ into CSA_IKFL.CSA_PROFILES cp
using ( 
  select * from CSA_IKFL.FORDEL$RELEASE14
) t
on (
      UPPER(TRIM( REGEXP_REPLACE (cp."SUR_NAME"||' '||cp."FIRST_NAME"||' '||cp."PATR_NAME",'( )+',' ')))=FIO
  and cp.BIRTHDATE = t.BIRTHDAY
  and REPLACE(cp."PASSPORT",' ','')=DUL
  and cp.TB=t.TB
)
when matched then update set cp.creation_type='POTENTIAL' where cp.creation_type is null
/

drop table CSA_IKFL.FORDEL$RELEASE14
/
