
CREATE OR REPLACE VIEW V_CLIENTS
AS
select   
           c."CLN_ID" AS CLIENT_ID,
           c."CLN_LNAME" AS SUR_NAME,
           c."CLN_FNAME" AS FIRST_NAME,
           c."CLN_MNAME" AS PATR_NAME,
           c."CLN_PASSWORD",
           c."CLN_LOGIN" AS LOGIN,
           c."CLN_TOKEN",
           c."CLN_RICH",
           c."CLN_SMS_PWD" AS "simple-auth-choice",
           c."CLN_LOCKED",
           c."CLN_LOCKED_COUNT",
           c."CLN_LOCK_REASON" AS LOCK_REASON,
           XMLTYPE(c."RGSTR_XML") AS XML_DATA,
           c."CLN_STATUS" AS STATE,
           c."SBB_ID",
           c."PWD_COUNT",
           c."PWD_SEANS_COUNT",
           c."PROG_FAIL",
           c."STOP_PROLONG",
           c."STOP_ESK_DATE",
           c."FIO_4FCERT",
           c."PASSP_TYPE" AS DOC_TYPE,
           c."PASSP_SERIAL" AS DOC_SERIES,
           c."PASSP_NUMBER" AS DOC_NUMBER,
           c."AGRNUM" AS AGREEMENT_NUMBER,
           c."CLN_GENDER" AS GENDER,
           c."CLN_BIRTH" AS BIRTHDAY,
           c."ACCEPTED_AGR_NUM",
           c."EDBO" AS IS_EDBO,
           c."COD_TIME",
           c."PROFILE_SAVED",
           c."AGRDATE" AS AGREEMENT_DATE,
           c."AGRDATE" AS INSERTION_OF_SERVICE,
           c."CREDIT_PLC",
           c."SMS_CARD",
           c."MDM_LAST_MODIFY",
           c."ACC_TIME",
           c."CARD_TIME",
           c."MET_TIME",
           c."CRED_TIME",
           c."LO_TIME",
           c."PRINT_CHEQUE",
           c."NO_VKLAD" AS IS_CARD,
           c."NO_OUR_TRBNK",
           c."RGN_CODE" AS RGN_CODE,
-- RGSTR_XML в этом XML поле содержатся все остальные данные.
-- Из каких тегов нужно их выбирать, описано в ТЗ по миграции

-- Пол NULL - не указан, 0 - женский, 1 - мужской

-- Статусы клиента
-- OK	Активный
-- BL	Блокирован
-- DL	Удален
-- DR	Черновик (анкета полностью или частично заполнена, договор не подписан)
-- остальные статусы имеют техническое значение, мигрировать этих клиентов нельзя

--тип паспорта (наименование по нашему справочнику)
p.ps_name AS DOC_NAME, 

-- подразделение Сбербанка
s.sbb_tb  AS TB,
s.sbb_osb AS OSB,
s.sbb_filial AS OFFICE

from ESKADM1.T_CLIENT c
LEFT OUTER JOIN ESKADM1.tt_passport_type p on p.ps_code = c.passp_type
LEFT OUTER JOIN ESKADM1.t_esk_sberbank s on s.esk_id = c.sbb_id

