package com.rssl.phizic.locale.entities;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.locale.ERIBLocaleConfig;
import com.rssl.phizic.locale.services.EribStaticMessageService;
import com.rssl.phizic.utils.StringHelper;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс для хранения связки bundle|key для мультиязычных текстовок
 * @author komarov
 * @ created 16.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class ERIBLocaleMessageResources
{
	private static final EribStaticMessageService service = new EribStaticMessageService();
	private static final String KEY_SEPARATOR = "|";

	private String localeId;//идентификатор локали.
	private Map<String, String> resourcesMap = new HashMap<String, String>();//мапа текстовок по ключам.

	/**
	 * Конструктор
	 * @param localeId идентификатор локали
	 */
	public ERIBLocaleMessageResources(String localeId)
	{
		this.localeId = localeId;
	}

	/**
	 * @return идентификатор локали
	 */
	public String getLocaleId()
	{
		return localeId;
	}

	/**
	 * @param localeId идентификатор локали
	 */
	public void setLocaleId(String localeId)
	{
		this.localeId = localeId;
	}

	/**
	 * @return мапа с текстовками
	 * ключ bundle|key
	 */
	public Map<String, String> getResourcesMap()
	{
		return resourcesMap;
	}

	/**
	 * @param resourcesMap мапа с текстовками
	 */
	public void setResourcesMap(Map<String, String> resourcesMap)
	{
		this.resourcesMap = resourcesMap;
	}

	/**
	 * Возвращает сообщение по ключу и бандлу
	 * @param key ключ
	 * @param bundle бандл
	 * @return сообщение
	 */
	public String getMessage(String key, String bundle)
	{
		return resourcesMap.get(bundle + KEY_SEPARATOR + key);
	}

	/**
	 * инициализация ресурсов
	 */
	public void initialize()
	{
		List<ERIBStaticMessage> list = null;
		try
		{
			list = service.getAll(localeId,  ConfigFactory.getConfig(ERIBLocaleConfig.class).getDbInstanceName());
		}
		catch (SystemException ignore)
		{
			list = Collections.emptyList();
		}

		for(ERIBStaticMessage message : list)
		{
			resourcesMap.put(message.getBundle() + KEY_SEPARATOR + message.getKey(), StringHelper.getEmptyIfNull(message.getMessage()));
		}
	}
}
