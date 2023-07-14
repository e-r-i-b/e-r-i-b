package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.PropertyHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.Properties;

/**
 * User: Balovtsev
 * Date: 19.02.13
 * Time: 11:04
 */
public class StoredResourceMessages
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static Properties messages = new Properties();
	static
	{
		messages = PropertyHelper.readProperties("storedResourcesMessages.properties");
	}

	/**
	 *
	 * @param  storedResource оффлайн версия продукта
	 * @return сообщение о том что информация по продукту берется из его оффлайн версии
	 */
	public static String getUnreachableMessageSystem(final AbstractStoredResource storedResource)
	{
		String message = messages.getProperty( storedResource.getResourceLink().getStoredResourceType().getName() );

		if (!StringHelper.isEmpty(message))
		{
			return String.format(message, storedResource.getEntityUpdateTime());
		}

		throw new RuntimeException("Не определен тип хранимой информации(" + storedResource.getResourceLink().getResourceType() + ").");
	}

	/**
	 *
	 * @return сообщение о недоступности сокращенной выписки
	 */
	public static String getUnreachableStatement()
	{
		return messages.getProperty("unreachable.statement");
	}

	/**
	 *
	 * @return сообщение о недоступности полной выписки
	 */
	public static String getUnreachableFullStatement()
	{
		return messages.getProperty("unreachable.full.statement");
	}

	/**
	 *
	 * @return сообщение о недоступности графической выписки
	 */
	public static String getUnreachableGraphStatement()
	{
		return messages.getProperty("unreachable.graph.statement");
	}
}
