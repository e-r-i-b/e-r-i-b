package com.rssl.phizic.rsa.senders.enumeration;

/**
 * @author tisov
 * @ created 07.07.15
 * @ $Author$
 * @ $Revision$
 * Тип запроса в систему фрод-мониторинга
 */
public enum  FraudMonitoringRequestType
{
	UPDATE_ACTIVITY,    //запрос на изменение вердикта по транзакции в ActivityEngine
	BY_DOCUMENT,        //запрос по транзакции на основе платежа/шаблона в AdaptiveAuthentication
	ANALYZE_BY_EVENT,   //запрос на анализ по транзакции-событию в AdaptiveAuthentication
	NOTIFY_BY_EVENT     //запрос-оповещение по транзакции-событию в AdaptiveAuthentication
}
