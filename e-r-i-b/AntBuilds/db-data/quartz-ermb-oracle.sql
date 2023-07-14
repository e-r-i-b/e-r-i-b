/*¬ качестве разделител€ запросов следует использовать "go" (с учЄтом регистра)*/

delete from qrtz_ermb_job_listeners
go

delete from qrtz_ermb_trigger_listeners
go

delete from qrtz_ermb_fired_triggers
go

delete from qrtz_ermb_simple_triggers
go

delete from qrtz_ermb_cron_triggers
go

delete from qrtz_ermb_blob_triggers
go

delete from qrtz_ermb_triggers
go

delete from qrtz_ermb_job_details
go

delete from qrtz_ermb_calendars
go

delete from qrtz_ermb_paused_trigger_grps
go

delete from qrtz_ermb_locks
go

delete from qrtz_ermb_scheduler_state
go

drop table qrtz_ermb_calendars
go

drop table qrtz_ermb_fired_triggers
go

drop table qrtz_ermb_trigger_listeners
go

drop table qrtz_ermb_blob_triggers
go

drop table qrtz_ermb_cron_triggers
go

drop table qrtz_ermb_simple_triggers
go

drop table qrtz_ermb_triggers
go

drop table qrtz_ermb_job_listeners
go

drop table qrtz_ermb_job_details
go

drop table qrtz_ermb_paused_trigger_grps
go

drop table qrtz_ermb_locks
go

drop table qrtz_ermb_scheduler_state
go

CREATE TABLE qrtz_ermb_job_details
  (
    JOB_NAME  VARCHAR2(80) NOT NULL,
    JOB_GROUP VARCHAR2(80) NOT NULL,
    DESCRIPTION VARCHAR2(120) NULL,
    JOB_CLASS_NAME   VARCHAR2(128) NOT NULL,
    IS_DURABLE VARCHAR2(1) NOT NULL,
    IS_VOLATILE VARCHAR2(1) NOT NULL,
    IS_STATEFUL VARCHAR2(1) NOT NULL,
    REQUESTS_RECOVERY VARCHAR2(1) NOT NULL,
    JOB_DATA BLOB NULL,
    PRIMARY KEY (JOB_NAME,JOB_GROUP)
)
go

CREATE TABLE qrtz_ermb_job_listeners
  (
    JOB_NAME  VARCHAR2(80) NOT NULL,
    JOB_GROUP VARCHAR2(80) NOT NULL,
    JOB_LISTENER VARCHAR2(80) NOT NULL,
    PRIMARY KEY (JOB_NAME,JOB_GROUP,JOB_LISTENER),
    FOREIGN KEY (JOB_NAME,JOB_GROUP)
  REFERENCES QRTZ_ERMB_JOB_DETAILS(JOB_NAME,JOB_GROUP)
)
go

CREATE TABLE qrtz_ermb_triggers
  (
    TRIGGER_NAME VARCHAR2(80) NOT NULL,
    TRIGGER_GROUP VARCHAR2(80) NOT NULL,
    JOB_NAME  VARCHAR2(80) NOT NULL,
    JOB_GROUP VARCHAR2(80) NOT NULL,
    IS_VOLATILE VARCHAR2(1) NOT NULL,
    DESCRIPTION VARCHAR2(120) NULL,
    NEXT_FIRE_TIME NUMBER(13) NULL,
    PREV_FIRE_TIME NUMBER(13) NULL,
    PRIORITY NUMBER(13) NULL,
    TRIGGER_STATE VARCHAR2(16) NOT NULL,
    TRIGGER_TYPE VARCHAR2(8) NOT NULL,
    START_TIME NUMBER(13) NOT NULL,
    END_TIME NUMBER(13) NULL,
    CALENDAR_NAME VARCHAR2(80) NULL,
    MISFIRE_INSTR NUMBER(2) NULL,
    JOB_DATA BLOB NULL,
    PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (JOB_NAME,JOB_GROUP)
  REFERENCES QRTZ_ERMB_JOB_DETAILS(JOB_NAME,JOB_GROUP)
)
go

CREATE TABLE qrtz_ermb_simple_triggers
  (
    TRIGGER_NAME VARCHAR2(80) NOT NULL,
    TRIGGER_GROUP VARCHAR2(80) NOT NULL,
    REPEAT_COUNT NUMBER(7) NOT NULL,
    REPEAT_INTERVAL NUMBER(12) NOT NULL,
    TIMES_TRIGGERED NUMBER(7) NOT NULL,
    PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_ERMB_TRIGGERS(TRIGGER_NAME,TRIGGER_GROUP)
)
go

CREATE TABLE qrtz_ermb_cron_triggers
  (
    TRIGGER_NAME VARCHAR2(80) NOT NULL,
    TRIGGER_GROUP VARCHAR2(80) NOT NULL,
    CRON_EXPRESSION VARCHAR2(80) NOT NULL,
    TIME_ZONE_ID VARCHAR2(80),
    PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_ERMB_TRIGGERS(TRIGGER_NAME,TRIGGER_GROUP)
)
go

CREATE TABLE qrtz_ermb_blob_triggers
  (
    TRIGGER_NAME VARCHAR2(80) NOT NULL,
    TRIGGER_GROUP VARCHAR2(80) NOT NULL,
    BLOB_DATA BLOB NULL,
    PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (TRIGGER_NAME,TRIGGER_GROUP)
        REFERENCES QRTZ_ERMB_TRIGGERS(TRIGGER_NAME,TRIGGER_GROUP)
)
go

CREATE TABLE qrtz_ermb_trigger_listeners
  (
    TRIGGER_NAME  VARCHAR2(80) NOT NULL,
    TRIGGER_GROUP VARCHAR2(80) NOT NULL,
    TRIGGER_LISTENER VARCHAR2(80) NOT NULL,
    PRIMARY KEY (TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_LISTENER),
    FOREIGN KEY (TRIGGER_NAME,TRIGGER_GROUP)
  REFERENCES QRTZ_ERMB_TRIGGERS(TRIGGER_NAME,TRIGGER_GROUP)
)
go

CREATE TABLE qrtz_ermb_calendars
  (
    CALENDAR_NAME  VARCHAR2(80) NOT NULL,
    CALENDAR BLOB NOT NULL,
    PRIMARY KEY (CALENDAR_NAME)
)
go

CREATE TABLE qrtz_ermb_paused_trigger_grps
  (
    TRIGGER_GROUP  VARCHAR2(80) NOT NULL,
    PRIMARY KEY (TRIGGER_GROUP)
)
go

CREATE TABLE qrtz_ermb_fired_triggers
  (
    ENTRY_ID VARCHAR2(95) NOT NULL,
    TRIGGER_NAME VARCHAR2(80) NOT NULL,
    TRIGGER_GROUP VARCHAR2(80) NOT NULL,
    IS_VOLATILE VARCHAR2(1) NOT NULL,
    INSTANCE_NAME VARCHAR2(80) NOT NULL,
    FIRED_TIME NUMBER(13) NOT NULL,
    PRIORITY NUMBER(13) NOT NULL,
    STATE VARCHAR2(16) NOT NULL,
    JOB_NAME VARCHAR2(80) NULL,
    JOB_GROUP VARCHAR2(80) NULL,
    IS_STATEFUL VARCHAR2(1) NULL,
    REQUESTS_RECOVERY VARCHAR2(1) NULL,
    PRIMARY KEY (ENTRY_ID)
)
go

CREATE TABLE qrtz_ermb_scheduler_state
  (
    INSTANCE_NAME VARCHAR2(80) NOT NULL,
    LAST_CHECKIN_TIME NUMBER(13) NOT NULL,
    CHECKIN_INTERVAL NUMBER(13) NOT NULL,
    PRIMARY KEY (INSTANCE_NAME)
)
go

CREATE TABLE qrtz_ermb_locks
  (
    LOCK_NAME  VARCHAR2(40) NOT NULL,
    PRIMARY KEY (LOCK_NAME)
)
go

INSERT INTO qrtz_ermb_locks values('TRIGGER_ACCESS')
go

INSERT INTO qrtz_ermb_locks values('JOB_ACCESS')
go

INSERT INTO qrtz_ermb_locks values('CALENDAR_ACCESS')
go

INSERT INTO qrtz_ermb_locks values('STATE_ACCESS')
go

INSERT INTO qrtz_ermb_locks values('MISFIRE_ACCESS')
go

create index idx_qrtz_ermb_j_req_recovery on qrtz_ermb_job_details(REQUESTS_RECOVERY)
go

create index idx_qrtz_ermb_t_next_fire_time on qrtz_ermb_triggers(NEXT_FIRE_TIME)
go

create index idx_qrtz_ermb_t_state on qrtz_ermb_triggers(TRIGGER_STATE)
go

create index idx_qrtz_ermb_t_nft_st on qrtz_ermb_triggers(NEXT_FIRE_TIME,TRIGGER_STATE)
go

create index idx_qrtz_ermb_t_volatile on qrtz_ermb_triggers(IS_VOLATILE)
go

create index idx_qrtz_ermb_ft_trig_name on qrtz_ermb_fired_triggers(TRIGGER_NAME)
go

create index idx_qrtz_ermb_ft_trig_group on qrtz_ermb_fired_triggers(TRIGGER_GROUP)
go

create index idx_qrtz_ermb_ft_trig_nm_gp on qrtz_ermb_fired_triggers(TRIGGER_NAME,TRIGGER_GROUP)
go

create index idx_qrtz_ermb_ft_trig_volatile on qrtz_ermb_fired_triggers(IS_VOLATILE)
go

create index idx_qrtz_ermb_ft_trig_inst_nm on qrtz_ermb_fired_triggers(INSTANCE_NAME)
go

create index idx_qrtz_ermb_ft_job_name on qrtz_ermb_fired_triggers(JOB_NAME)
go

create index idx_qrtz_ermb_ft_job_group on qrtz_ermb_fired_triggers(JOB_GROUP)
go

create index idx_qrtz_ermb_ft_job_stateful on qrtz_ermb_fired_triggers(IS_STATEFUL)
go

create index idx_qrtz_ermb_ft_job_req_rec on qrtz_ermb_fired_triggers(REQUESTS_RECOVERY)
go
