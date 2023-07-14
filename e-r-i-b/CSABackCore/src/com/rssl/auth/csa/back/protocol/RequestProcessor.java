package com.rssl.auth.csa.back.protocol;

/**
 * @author krenev
 * @ created 22.08.2012
 * @ $Author$
 * @ $Revision$
 * Обработчик запроса
 */

public interface RequestProcessor
{
	/**
	 * Обработать запрос и вернуть результат
	 * @param requestInfo информация о запросе
	 * @return информация об ответе
	 */
	ResponseInfo process(RequestInfo requestInfo) throws Exception;

	/**
	 * Доступна ли данная обработка в режиме standIn ЦСА
	 * @return true - доступна
	 */
	boolean isAccessStandIn();
}
