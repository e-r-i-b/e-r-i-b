package com.rssl.phizic.ws.esberiblistener.esberib;

import com.rssl.phizic.ws.esberiblistener.esberib.generated.IFXRq_Type;
import com.rssl.phizic.ws.esberiblistener.esberib.generated.IFXRs_Type;

/**
 * Интерфейс обработчика входящих сообщений от шины
 * @author gladishev
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 */
public interface EsbEribBackService
{
	/**
	 * Обработка запроса
	 * @param parameters - параметры запроса
	 * @return - ответ
	 */
	public IFXRs_Type doIFX(IFXRq_Type parameters);
}
