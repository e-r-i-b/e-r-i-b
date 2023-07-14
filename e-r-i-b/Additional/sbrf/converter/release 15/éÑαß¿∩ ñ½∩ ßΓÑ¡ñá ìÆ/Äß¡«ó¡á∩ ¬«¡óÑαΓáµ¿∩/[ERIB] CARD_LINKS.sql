alter table SRB_IKFL.CARD_LINKS rename to FORDEL$CARD_LINKS;

alter table FORDEL$CARD_LINKS rename constraint PK_CARD_LINKS to FORDEL$CARD_LINKS_C_1;
alter table FORDEL$CARD_LINKS rename constraint FK_CARD_LIN_FK_CARDLI_LOGINS to FORDEL$CARD_LINKS_C_5;

alter table ERMB_PROFILE drop constraint FK_FOREG_PRODUCT;
alter table SENDED_ABSTRACTS drop constraint FK_SENDED_A_TO_CARD_LIN;
alter table STORED_CARD drop constraint FK_STORED_C_TO_LINK_REF;

alter index DXFK_CARDLINKS_TO_LOGINS rename to FORDEL$CARD_LINKS_IDX_2;
alter index UNIQ_CARD_NUMBER rename to FORDEL$CARD_LINKS_IDX_3;
alter index PK_CARD_LINKS rename to FORDEL$CARD_LINKS_IDX_4;

create table SRB_IKFL.CARD_LINKS as 
select /*+parallel(cl, 32)*/
  ID,
  EXTERNAL_ID,
  LOGIN_ID,
  CARD_NUMBER,
  CARD_NAME,
  SHOW_IN_MAIN,
  SHOW_IN_SYSTEM,
  EXPIRE_DATE,
  SHOW_OPERATIONS,
  CARD_PRIMARY_ACCOUNT,
  IS_MAIN,
  MAIN_CARD_NUMBER,
  SHOW_IN_MOBILE,
  OTP_GET,
  OTP_USE,
  DESCRIPTION,
  CURRENCY,
  KIND,
  SUB_KIND,
  SHOW_IN_ATM,
  '1' SHOW_IN_SOCIAL,
  ERMB_NOTIFICATION,
  ERMB_SMS_ALIAS,
  GFL_TB,
  GFL_OSB,
  GFL_VSP,
  MB_USER_ID,
  SMS_AUTO_ALIAS,
  ADDITIONAL_CARD_TYPE,
  POSITION_NUMBER,
  SHOW_IN_SMS,
  USE_REPORT_DELIVERY,
  EMAIL_ADDRESS,
  REPORT_DELIVERY_TYPE,
  REPORT_DELIVERY_LANGUAGE,
  CLIENT_ID,
  to_timestamp('18.11.2014 00:00:00', 'dd.mm.yyyy hh24:mi:ss') CREATION_DATE  
from SRB_IKFL.FORDEL$CARD_LINKS cl;

alter table SRB_IKFL.CARD_LINKS modify SHOW_IN_SOCIAL not null novalidate;
alter table SRB_IKFL.CARD_LINKS modify CREATION_DATE not null novalidate;

alter table SRB_IKFL.CARD_LINKS add constraint PK_CARD_LINKS primary key (ID) using index (
	create unique index SRB_IKFL.PK_CARD_LINKS on SRB_IKFL.CARD_LINKS (ID) parallel 32 tablespace indx
) enable novalidate;

create index SRB_IKFL.DXFK_CARDLINKS_TO_LOGINS on SRB_IKFL.CARD_LINKS (LOGIN_ID) parallel 32 tablespace indx ;
create unique index SRB_IKFL.UNIQ_CARD_NUMBER on SRB_IKFL.CARD_LINKS (CARD_NUMBER, LOGIN_ID) parallel 32 tablespace indx ;

alter table SRB_IKFL.CARD_LINKS 
	add constraint FK_CARD_LIN_FK_CARDLI_LOGINS foreign key (LOGIN_ID)
		references SRB_IKFL.LOGINS (ID) enable novalidate;

alter table SRB_IKFL.ERMB_PROFILE 
	add constraint FK_FOREG_PRODUCT foreign key (FOREG_PRODUCT_ID)
		references SRB_IKFL.CARD_LINKS (ID) on delete set null enable novalidate;

alter table SRB_IKFL.SENDED_ABSTRACTS 
	add constraint FK_SENDED_A_TO_CARD_LIN foreign key (CARDLINK_ID)
		references SRB_IKFL.CARD_LINKS (ID) enable novalidate;

alter table SRB_IKFL.STORED_CARD 
	add constraint FK_STORED_C_TO_LINK_REF foreign key (RESOURCE_ID)
		references SRB_IKFL.CARD_LINKS (ID) on delete cascade enable novalidate;
		
alter index SRB_IKFL.DXFK_CARDLINKS_TO_LOGINS noparallel;
alter index SRB_IKFL.UNIQ_CARD_NUMBER noparallel;
alter index SRB_IKFL.PK_CARD_LINKS noparallel;		