package com.rssl.phizic.limits.handlers;

import org.w3c.dom.Document;

/**
 * @author osminin
 * @ created 21.01.14
 * @ $Author$
 * @ $Revision$
 *
 * Процессор для обработки запросов к транзакциям документов
 */
public interface TransactionProcessor
{
	/**
	 * Выполнить
	 * @param request запрос
	 */
	void process(Document request) throws Exception;
}
