-- Номер ревизии: 69560
-- Номер версии: 1.18
-- Комментарий: Рефакторинг джоба LoadMBKRegistrationsJob(Загрузка связок МБК, задача Миграция ЕРМБ на лету)
-- Заменил таблицу состояния [обработки] связок на Таблицу [конечных] результатов обработки

drop table MBK_REGISTRATIONS;

create table MBK_REGISTRATION_RESULTS
(
  ID             INTEGER NOT NULL,
  RESULT_CODE    VARCHAR2(20) NOT NULL,
  ERROR_DESCR    VARCHAR2(500),
  LAST_MODIFIED  TIMESTAMP(6) NOT NULL,
  constraint MBK_REGISTRATION_RESULTS_PK primary key (ID)
)
;
-- Номер ревизии: 71973
-- Номер версии: 1.18
-- Комментарий: Унификация формата телефона - вспомогательные таблицы ермб

UPDATE ERMB_PHONES
   SET PHONE_NUMBER = SUBSTR (PHONE_NUMBER, 2);

ALTER TABLE ERMB_PHONES MODIFY PHONE_NUMBER VARCHAR2(10);
