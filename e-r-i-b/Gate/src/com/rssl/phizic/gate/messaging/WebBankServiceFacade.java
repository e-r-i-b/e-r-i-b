package com.rssl.phizic.gate.messaging;

import com.rssl.phizic.gate.cache.MessagesCacheManager;

/**
 * @author Evgrafov
 * @ created 28.08.2006
 * @ $Author: krenev $
 * @ $Revision: 10491 $
 * @deprecated не соответсвует модели гейта. Работа с внешней системой (RetailJNI, ЦОД)
 * должна осуществляться в реализации гейта. Общение с бизнесом только через java-интерфейс.
 */
@Deprecated
public interface WebBankServiceFacade extends OfflineServiceFacade, OnlineServiceFacade
{
	/**
	 * Получение кеш менеджера сообщений
	 * @return MessagesCacheManager
	 */
	MessagesCacheManager getMessagesCacheManager();

}
