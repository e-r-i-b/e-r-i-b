package com.rssl.phizic.cache;

import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.dataaccess.query.MultiLocaleBeanQuery;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.cache.OnCacheOutOfDateListener;
import org.apache.commons.logging.Log;

/**
 * @author usachev
 * @ created 21.04.15
 * @ $Author$
 * @ $Revision$
 * Класс предназначен для использования в кеше com.rssl.phizic.utils.cache.Cache в качестве источника данных
 */
public class DataBaseSMSResourceListener implements OnCacheOutOfDateListener<String, String>
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Gate);
	private static final String SEPARATOR = "\\^";

	public String onRefresh(final String key)
	{
		String message = null;
		try
		{
			BeanQuery query = null;
			String[] params = key.split(SEPARATOR);
			if (params.length == 1)
			{
				query = new BeanQuery(new Object(), "com.rssl.auth.csa.back.messages.CSASmsResourcesOperation.getText", null);
			}
			else
				query = new MultiLocaleBeanQuery(new Object(), "com.rssl.auth.csa.back.messages.CSASmsResourcesOperation.getText", null, params[1]);
			query.setParameter("key", params[0]);
			message =  query.executeUnique();
		}
		catch (Exception e)
		{
			log.trace("Ошибка при получении текста смс сообщения с ключом " + key, e);
		}
		return message;
	}
}
