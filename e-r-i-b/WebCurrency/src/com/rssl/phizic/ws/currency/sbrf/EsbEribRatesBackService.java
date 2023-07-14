package com.rssl.phizic.ws.currency.sbrf;

/**
 * Обработчик входящих запросов от шины по обновлению курсов валют
 *
 * @author gladishev
 * @ created 29.10.13
 * @ $Author$
 * @ $Revision$
 */
public interface EsbEribRatesBackService
{
	/**
	 * Обработка запроса
	 * @param request - запрос
	 * @return - ответ
	 */
	public String doIFX(String request) throws java.rmi.RemoteException;
}
