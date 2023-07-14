package com.rssl.phizic.gate.documents;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Map;

/**
 * @author Krenev
 * @ created 16.08.2007
 * @ $Author$
 * @ $Revision$
 */
public interface DocumentSender
{
	/**
	 * Установить параметры
	 * @param params параметры
	 */
	void setParameters(Map<String,?> params);
	/**
	 * послать документ
	 * @param document документ
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void send(GateDocument document) throws GateException, GateLogicException;

	/**
	 * послать документ
	 * @param document документ
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void repeatSend(GateDocument document) throws GateException, GateLogicException;

	/**
	 * подготовить документ
	 * @param document документ
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void prepare(GateDocument document) throws GateException, GateLogicException;

	/**
	 * отозвать документ
	 * @param document документ
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void rollback(WithdrawDocument document) throws GateException, GateLogicException;

	/**
	 * подтвердить платеж
	 * @param document платеж для подтверждения
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void confirm(GateDocument document) throws GateException, GateLogicException;

	/**
	 * валидация платежа
	 * @param document  платеж
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void validate(GateDocument document) throws GateException, GateLogicException;
}
