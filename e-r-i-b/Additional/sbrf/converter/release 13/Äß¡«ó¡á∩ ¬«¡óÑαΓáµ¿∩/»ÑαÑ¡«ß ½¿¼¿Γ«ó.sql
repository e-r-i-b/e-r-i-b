-- Номер ревизии: 57540
-- Комментарий: Лимиты в многоблочности : Доработки ЕРИБ(ч5) 
DECLARE
	TYPE docType IS RECORD (operDate BUSINESS_DOCUMENTS.OPERATION_DATE%TYPE, operUID BUSINESS_DOCUMENTS.OPERATION_UID%TYPE, loginId BUSINESS_DOCUMENTS.LOGIN_ID%TYPE);
	doc                 docType;
	limits_info         VARCHAR2(4000);
	groupRiskExId       VARCHAR2(35);
	personId            INTEGER;
	amount              NUMBER(19,4);
	currency            CHAR(3);
	channel             VARCHAR2(25);
	externalCard        VARCHAR2(19);
	externalPhone       VARCHAR2(16);
BEGIN
	FOR documentId IN (select DOCUMENT_ID from USERS_LIMITS_JOURNAL where STATE = 'ALLOWED' and CREATION_DATE > SYSDATE - 1 group by DOCUMENT_ID) LOOP 
		select OPERATION_DATE, OPERATION_UID, LOGIN_ID into doc from BUSINESS_DOCUMENTS where documentId.DOCUMENT_ID = ID;
		select ID into personId from USERS where doc.loginId = LOGIN_ID;
		-- формируем информацию по лимитам для документа
		FOR record IN (select * from USERS_LIMITS_JOURNAL where documentId.DOCUMENT_ID = DOCUMENT_ID) LOOP 
			-- учитываем лимиты внешнему получателю на карту
			IF(record.EXTERNAL_CARD is not NULL) THEN
				externalCard := record.EXTERNAL_CARD;
			END IF;
			-- учитываем лимиты внешнему получателю на телефон
			IF(record.EXTERNAL_PHONE is not NULL) THEN
				externalPhone := record.EXTERNAL_PHONE;            
			END IF;
			-- собираем информацию по сработавшим лимитам
			limits_info := limits_info || record.LIMIT_TYPE || ',' || record.RESTRICTION_TYPE || ',';
			IF(record.GROUP_RISK_ID is not NULL) THEN
				select EXTERNAL_ID into groupRiskExId from GROUPS_RISK where ID = record.GROUP_RISK_ID;  
				limits_info := limits_info || groupRiskExId; 
			END IF;    
			limits_info := limits_info || ';';
			-- не усложняем скрипт лишними ифами для значений из документа, просто переприсваиваем одни и те же значения
			channel := record.CHANNEL_TYPE;     
			amount := record.AMOUNT;            
			currency := record.AMOUNT_CURRENCY; 
		END LOOP;
		-- теперь инсерт в новую таблицу
		insert into DOCUMENT_OPERATIONS_JOURNAL(EXTERNAL_ID, DOCUMENT_EXTERNAL_ID, OPERATION_DATE, PROFILE_ID, AMOUNT, AMOUNT_CURRENCY, OPERATION_TYPE, CHANNEL_TYPE, LIMITS_INFO, EXTERNAL_CARD, EXTERNAL_PHONE)
		values (SYS_GUID, doc.operUID, doc.operDate, personId, amount, currency, 'commit', channel, limits_info, externalCard, externalPhone);
		-- сбрасываем чтобы не учлись в слеюущих итерациях
		limits_info := null;
		externalPhone := null;
		externalCard := null;  
		groupRiskExId := null;
		channel := null;     
		amount := null;            
		currency := null;
	END LOOP;
END;
/
/*Удаляем USERS_LIMITS_JOURNAL если нужно
drop table USERS_LIMITS_JOURNAL
/
*/