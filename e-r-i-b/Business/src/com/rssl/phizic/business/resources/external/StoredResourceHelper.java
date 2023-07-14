package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.ClassHelper;

/**
 * User: Balovtsev
 * Date: 16.11.2012
 * Time: 10:05:42
 */
public class StoredResourceHelper
{
	private static final ExternalResourceService service = new ExternalResourceService();

	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 *
	 * Осуществляет поиск сохраненной по продукту информации по link продукта
	 *
	 * @param link линк для поиска
	 * @param e исключение, которое побудило вызов данного метода.
	 * @return AbstractStoredResource
	 */
	public static AbstractStoredResource findStoredResource(ExternalResourceLink link, Exception e)
	{
		try
		{
			AbstractStoredResource resource = service.findStoredResourceByResourceLink(link);
			if (e instanceof InactiveExternalSystemException)
			{
				PersonHelper.setPersonDataNeedUpdate();

				if (resource == null)
					throw (InactiveExternalSystemException) e;
			}

			return resource;
		}
		catch (BusinessException ex)
		{
			log.error("Ошибка во время поиска оффлайн продукта", ex);
			return null;
		}
	}

	/**
	 *
	 * @param link   ссылка на link, по которому будем искать сохранённую версию продукта
	 * @param object продукт из которого берутся данные для обновления
	 *
	 */
	public static void updateStoredResource(ExternalResourceLink link, Object object)
	{
		// Не надо добавлять счет к линкам, которые не сохраняются в БД. Фича импорта счетов из v6.
		if (object == null || link == null || link.getId() == null)
		{
			return;
		}

		try
		{
			AbstractStoredResource storedResource = service.findStoredResourceByResourceLink(link);
			if (storedResource == null)
			{
				storedResource = loadStoredResourceInstance(link);
				storedResource.setResourceLink(link);
			}

			if (storedResource.needUpdate(object))
			{
				storedResource.update(object);
				service.addOrUpdateStoredResource(storedResource);
			}
		}
		catch (Exception e)
		{
			log.error("Ошибка во время обновления информации оффлайн продукта", e);
		}
	}

	/**
	 *
	 * @param link   ссылка на link, по которому будем искать сохранённую версию продукта
	 * @param object продукт из которого берутся данные для обновления дополнительной информации
	 *
	 */
	public static void updateStoredResourceInfo(ExternalResourceLink link, Object object)
	{
		if (object == null || link == null)
		{
			return;
		}

		try
		{
			AbstractStoredResource storedResource = service.findStoredResourceByResourceLink(link);
			if (storedResource == null)
			{
				storedResource = loadStoredResourceInstance(link);
				storedResource.setResourceLink(link);
			}

			if (storedResource.needUpdateInfo(object))
			{
				storedResource.updateInfo(object);
				service.addOrUpdateStoredResource(storedResource);
			}
		}
		catch (Exception e)
		{
			log.error("Ошибка во время обновления дополнительной информации оффлайн продукта", e);
		}
	}

	public static AbstractStoredResource loadStoredResourceInstance(ExternalResourceLink link) throws BusinessException
	{
		try
		{
			return (AbstractStoredResource) ClassHelper.loadClass(link.getStoredResourceType().getName()).newInstance();
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка при попытке создания экземляра класса оффлайн продукта.", e);
		}
	}
}
