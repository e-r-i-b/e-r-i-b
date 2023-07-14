drop sequence s_payments_movs
/
create sequence s_payments_movs
	increment by 1
	start with 0
	nomaxvalue
	minvalue 0
	nocycle
	noorder
/
create table MOVE_PAYMENTS_MONITOR  ( 
	ID        	NUMBER(15,5) NULL,
	MIN_ID    	NUMBER(15,5) NULL,
	MAX_ID    	NUMBER(15,5) NULL,
	CNT       	NUMBER(15,5) NULL,
	TIME_START	TIMESTAMP NULL,
	TIME_STOP 	TIMESTAMP NULL,
    CONSTRAINT PK_MOVE_PAYMENTS_MONITOR PRIMARY KEY(ID) 
)
/