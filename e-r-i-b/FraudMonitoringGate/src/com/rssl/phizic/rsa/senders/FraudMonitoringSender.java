package com.rssl.phizic.rsa.senders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.senders.enumeration.FraudMonitoringRequestSendingType;
import com.rssl.phizic.rsa.senders.initialization.InitializationData;

/**
 * Интерфейс сендеров во Фрод-мониторинг
 *
 * @author khudyakov
 * @ created 03.02.15
 * @ $Author$
 * @ $Revision$
 */
public interface FraudMonitoringSender<RQ, ID extends InitializationData>
{
	/**
	 * Инициализация сендера
	 * @param data - данные для инициализации
	 */
	void initialize(ID data);

	/**
	 * @return данные инициализации
	 */
	ID getInitializationData();

	/**
	 * Отправить запрос во Фрод-мониторинг
	 */
	void send() throws GateLogicException;

	/**
	 * Является ли сендер заглушечным
	 * @return заглушка
	 */
	boolean isNull();

	/**
	 * Сериализованный в xml-документ запрос
	 * @return сериализованный экземпляр
	 */
	String getRequestBody() throws GateException, GateLogicException;

	FraudMonitoringRequestSendingType getSendingType();

}
