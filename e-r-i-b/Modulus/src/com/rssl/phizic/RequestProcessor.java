package com.rssl.phizic;

/**
 * @author Erkin
 * @ created 02.08.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Обработчик запросов
 */
public interface RequestProcessor<Rq extends Request, Rs extends Response>
{
	/**
	 * Обработать запрос
	 * @param request - запрос (never null)
	 * @return ответ
	 */
	Rs processRequest(Rq request) throws Exception;

	/**
	 * @return тип обрабатываемого запроса
	 */
	String getRequestName();
}
