package com.rssl.phizic.ws.esberiblistener.depo;

import java.rmi.RemoteException;

/**
 * Интерфейс обработчика входящих сообщений по депозитарию от шины
 *
 * @author gladishev
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 */
public interface EsbEribDepoBackService
{
	/**
	 * Обработка запроса
	 * @param req - запрос
	 * @return - ответ
	 */
	public String doIFX(String req) throws RemoteException;
}
