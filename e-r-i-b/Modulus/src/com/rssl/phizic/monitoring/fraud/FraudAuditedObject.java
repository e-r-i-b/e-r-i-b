package com.rssl.phizic.monitoring.fraud;

/**
 * Проверяемый в ВС ФМ объект
 *
 * @author khudyakov
 * @ created 03.07.15
 * @ $Author$
 * @ $Revision$
 */
public interface FraudAuditedObject
{
	/**
	 * Получить идентификатор соответствующией фрод-транзакции
	 * @return идентификатор транзакции
	 */
	String getClientTransactionId();

	/**
	 * Положить в документ идентификатор соответствующей фрод-транзакции
	 * @param transactionId идентификатор транзакции
	 */
	void setClientTransactionId(String transactionId);

	/**
	 * Установка причины отказа документа
	 * @param refusingReason причина отказа документа
	 */
	void setRefusingReason(String refusingReason);
}
