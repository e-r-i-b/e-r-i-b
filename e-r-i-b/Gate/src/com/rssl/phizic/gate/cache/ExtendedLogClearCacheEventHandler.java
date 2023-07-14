package com.rssl.phizic.gate.cache;

import com.rssl.phizic.events.EventHandler;
import com.rssl.phizic.logging.extendedLogging.ClientExtendedLoggingService;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;

/**
 * Обработчик события очистки кэша записей ресширенного логирования
 * @author gladishev
 * @ created 21.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedLogClearCacheEventHandler implements EventHandler<ExtendedLogClearCacheEvent>
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Cache);

	public void process(ExtendedLogClearCacheEvent event) throws Exception
	{
		try
		{
			ClientExtendedLoggingService.getInstance().clearCache(event.getLoginId());
		}
		catch (Exception e)
		{
			log.error("Ошибка при очистке кеша расширенного логирования.", e);
		}
	}
}
