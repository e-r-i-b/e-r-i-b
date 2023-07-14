package com.rssl.phizic.ws.esberiblistener.pfr;

import com.rssl.phizic.ws.esberiblistener.pfr.generated.PfrDoneRsType;
import com.rssl.phizic.ws.esberiblistener.pfr.generated.PfrDoneRqType;

import java.rmi.RemoteException;


/**
 * Интерфейс обработчика входящих сообщений по ПФР от шины
 *
 * @author gladishev
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 */
public interface EsbEribPFRBackService
{
	/**
	 * Обработка запроса
	 * @param request - запрос
	 * @return - ответ
	 */
	public PfrDoneRsType pfrDone(PfrDoneRqType request) throws RemoteException;
}
