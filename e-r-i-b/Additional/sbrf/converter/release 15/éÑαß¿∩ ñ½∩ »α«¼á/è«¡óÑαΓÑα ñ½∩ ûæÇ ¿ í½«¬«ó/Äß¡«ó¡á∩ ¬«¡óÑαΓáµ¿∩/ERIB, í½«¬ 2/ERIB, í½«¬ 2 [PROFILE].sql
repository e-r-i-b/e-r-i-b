alter session set CURRENT_SCHEMA = SRB_IKFL2;

alter table SRB_IKFL2.PROFILE rename to FORDEL$PROFILE;

alter table SRB_IKFL2.FORDEL$PROFILE rename constraint PK_PROFILE to FORDEL$PROFILE_C_1;
alter table SRB_IKFL2.FORDEL$PROFILE rename constraint FK_PROFILE_REFERENCE_LOGINS to FORDEL$PROFILE_C_8;
alter table SRB_IKFL2.FORDEL$PROFILE rename constraint FK_PROFILE_FK_AVATAR_USER_IMA to FORDEL$PROFILE_C_9;
alter table SRB_IKFL2.FORDEL$PROFILE rename constraint FK_PROFILE_REG_REF_REGIONS to FORDEL$PROFILE_C_10;

alter table SRB_IKFL2.WEB_PAGES drop constraint FK_PROFILE_TO_WEB_PAGES;

alter index SRB_IKFL2.I_PROFILE_ALF_STATISTICS rename to FORDEL$PROFILE_IDX_2;
alter index SRB_IKFL2.IDX_PROFILE_LOGIN rename to FORDEL$PROFILE_IDX_3;
alter index SRB_IKFL2.DXREG_REF rename to FORDEL$PROFILE_IDX_4;
alter index SRB_IKFL2.DXFK_AVATAR rename to FORDEL$PROFILE_IDX_5;
alter index SRB_IKFL2.DXFK_PROFILE_SKIN_ID rename to FORDEL$PROFILE_IDX_6;
alter index SRB_IKFL2.PK_PROFILE rename to FORDEL$PROFILE_IDX_7;

alter session force parallel ddl parallel 32;

create table PROFILE tablespace USER_DATA nologging as
select /*+parallel(p, 32)*/
  ID,
  LOGIN_ID,
  REGION_ID,
  SKIN_ID,
  SHOW_PERSONAL_FINANCE,
  REGION_SELECTED,
  MOBILE_WALLET_AMOUNT,
  MOBILE_WALLET_CURRENCY,
  case 
   when TARIF_PLAN_CODE = 'PREMIER' then '1'
   when TARIF_PLAN_CODE = 'GOLD'    then '2'
   when TARIF_PLAN_CODE = 'FIRST'   then '3'
   else null
  end as TARIF_PLAN_CODE,
  REG_WINDOW_SHOW_COUNT,
  STASH,
  START_USING_PERSONAL_FINANCE,
  LAST_USING_FINANCES_DATE,
  USING_FINANCES_COUNT,
  USING_ALF_EVERY_THREE_DAYS_NUM,
  LAST_UPDATE_OPER_CLAIMS_DATE,
  AVATAR_IMAGE_ID
from FORDEL$PROFILE p;

alter table PROFILE modify (LOGIN_ID not null enable novalidate);

create unique index SRB_IKFL2.PK_PROFILE on SRB_IKFL2.PROFILE (ID) parallel 32 tablespace USER_DATA_IDX;
  
alter table SRB_IKFL2.PROFILE add constraint PK_PROFILE primary key (ID) using index SRB_IKFL2.PK_PROFILE enable novalidate;

create index SRB_IKFL2.I_PROFILE_ALF_STATISTICS ON SRB_IKFL2.PROFILE (
  DECODE(USING_FINANCES_COUNT,0,NULL,TO_CHAR(TRUNC(USING_ALF_EVERY_THREE_DAYS_NUM*100/USING_FINANCES_COUNT)))
) parallel 32 tablespace USER_DATA_IDX; 

create unique index SRB_IKFL2.IDX_PROFILE_LOGIN ON SRB_IKFL2.PROFILE (LOGIN_ID) parallel 32 tablespace USER_DATA_IDX; 

create index SRB_IKFL2.DXREG_REF ON SRB_IKFL2.PROFILE (REGION_ID) parallel 32 tablespace USER_DATA_IDX; 

create index SRB_IKFL2.DXFK_AVATAR ON SRB_IKFL2.PROFILE (AVATAR_IMAGE_ID) parallel 32 tablespace USER_DATA_IDX;

create index SRB_IKFL2.DXFK_PROFILE_SKIN_ID on SRB_IKFL2.PROFILE (SKIN_ID) parallel 32 tablespace USER_DATA_IDX;

alter table SRB_IKFL2.PROFILE 
  add constraint FK_PROFILE_REFERENCE_LOGINS foreign key (LOGIN_ID)
    references SRB_IKFL2.LOGINS (ID) enable novalidate;

alter table SRB_IKFL2.PROFILE 
  add constraint FK_PROFILE_FK_AVATAR_USER_IMA foreign key (AVATAR_IMAGE_ID)
    references SRB_IKFL2.USER_IMAGES (ID) enable novalidate;

alter table SRB_IKFL2.PROFILE 
  add constraint FK_PROFILE_REG_REF_REGIONS foreign key (REGION_ID)
    references SRB_IKFL2.REGIONS (ID) enable novalidate;

alter table SRB_IKFL2.WEB_PAGES 
  add constraint FK_PROFILE_TO_WEB_PAGES foreign key (PROFILE_ID)
    references SRB_IKFL2.PROFILE (ID) enable novalidate;
	
alter index SRB_IKFL2.I_PROFILE_ALF_STATISTICS noparallel;
alter index SRB_IKFL2.IDX_PROFILE_LOGIN noparallel;
alter index SRB_IKFL2.DXREG_REF noparallel;
alter index SRB_IKFL2.DXFK_AVATAR noparallel;
alter index SRB_IKFL2.DXFK_PROFILE_SKIN_ID noparallel;
alter index SRB_IKFL2.PK_PROFILE noparallel;	
	