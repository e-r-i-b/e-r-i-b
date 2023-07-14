package com.rssl.phizic.gate.monitoring.fraud.jms.processors;

/**
 * Интерфейс обработчиков запроса от ВС ФМ
 *
 * @author khudyakov
 * @ created 13.06.15
 * @ $Author$
 * @ $Revision$
 */
public interface RequestProcessor
{
	/**
	 * @return true - ножно обработать
	 */
	boolean isEnabled();

	/**
	 * Выполнить действия над запросом
	 */
	void process() throws Exception;

	/**
	 * Откатить обработку
	 */
	void rollback();
}
