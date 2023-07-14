package com.rssl.phizic.dataaccess.common.counters;

import static com.rssl.phizic.dataaccess.common.counters.CounterNameGenerator.*;

/**
 * @author Barinov
 * @ created 14.10.2011
 * @ $Author$
 * @ $Revision$
 */

public interface Counters
{
	final long MAX_DOCUMENT_NUMBER = 999999L;
	final Counter UNLOAD_PRODUCT_NUMBER = Counter.createExtendedCounter("UNLOAD_PRODUCT", MAX_DOCUMENT_NUMBER, EVERY_DAY);
	final Counter AGREEMENT_NUMBER = Counter.createSimpleCounter("AGREEMENTNUMBER");
	final Counter CMS_MSG_NUMBER = Counter.createSimpleCounter("CMSMessages");
	final Counter CURRENT_OPERATION = Counter.createSimpleCounter("CUROPER");
	final Counter LETTER_NUMBER = Counter.createSimpleCounter("LetterNumber");
	final Counter PIN_REQUEST_NUMBER = Counter.createSimpleCounter("PINREQUESTNUMBER");
	final Counter CARDS_REQUEST_NUMBER = Counter.createSimpleCounter("CARDSREQUESTNUMBER");
	final Counter UNLOADING_NUMBER = Counter.createSimpleCounter("unloadingNumber");        //Порядковый номер выгрузки
	final Counter UNLOAD_VIRTUAL_CARD_CLAIM = Counter.createExtendedCounter("UNL_V_CARDCLAIM", MAX_DOCUMENT_NUMBER, EVERY_DAY);
	final Counter PEREODICAL_QRTZ_TRIGGER_ENTRY = Counter.createSimpleCounter("PEREODICAL_TRIGGER");
	final Counter MB_POKET_REGISTRATION_NUMBER = Counter.createSimpleCounter("MB_POKET_REG_NUM");//порядковый номера регистрационного пакета МБ
	final Counter ERMB_NEW_CLIENT_SERVICE = Counter.createSimpleCounter("ERMB_NEW_CLIENT_SERVICE"); // идентификатор запроса веб-сервиса уведомления ОСС о новых клиентах
	final Counter MB_SMS_ID = Counter.createSimpleCounter("MB_SMS_ID"); // уникальное значение СМС
	final Counter ERMB_MIGRATION_LOAD_LOG_NUMBER = Counter.createExtendedCounter("LOAD_LOG_NUMBER", Long.MAX_VALUE, EVERY_DAY);
	final Counter ERMB_MIGRATION_MIGRATE_LOG_NUMBER = Counter.createExtendedCounter("MIGRATE_LOG_NUMBER", Long.MAX_VALUE, EVERY_DAY);
	final Counter ERMB_MIGRATION_REVERT_LOG_NUMBER = Counter.createExtendedCounter("REVERT_LOG_NUMBER", Long.MAX_VALUE, EVERY_DAY);
	final Counter ERMB_MIGRATION_CM_REPORT_NUMBER = Counter.createExtendedCounter("CM_REPORT_NUMBER", Long.MAX_VALUE, EVERY_DAY);
	final Counter ERMB_MIGRATION_CC_REPORT_NUMBER = Counter.createExtendedCounter("CC_REPORT_NUMBER", Long.MAX_VALUE, EVERY_DAY);
	final Counter CARD_OFFER_LOAD_OPERATION = Counter.createExtendedCounter("CARD_OFFER_LOAD", Long.MAX_VALUE, EVERY_DAY);


}
