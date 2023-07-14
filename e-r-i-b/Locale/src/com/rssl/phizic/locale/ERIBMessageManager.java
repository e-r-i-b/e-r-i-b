package com.rssl.phizic.locale;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.locale.ERIBLocaleConfig;
import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.events.EventSender;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.locale.entities.ERIBLocaleMessageResources;
import com.rssl.phizic.locale.events.UpdateLocaleType;
import com.rssl.phizic.locale.events.UpdateMessagesEvent;
import com.rssl.phizic.locale.services.MultiInstanceEribLocaleService;
import com.rssl.phizic.locale.utils.LocaleHelper;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Класс для работы со статическими мультиязычными текстовками
 * @author komarov
 * @ created 16.09.2014
 * @ $Author$
 * @ $Revision$
 *
 */
public class ERIBMessageManager
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final AtomicLong lastUpdateDate = new AtomicLong(0);
	private static final Object LOCK = new Object();
	private static volatile boolean isSynchStart = false;

	private static final MultiInstanceEribLocaleService localesService = new MultiInstanceEribLocaleService();
	private static final Map<String, ERIBLocaleMessageResources> localeMessages = new HashMap<String, ERIBLocaleMessageResources>();
	private static final Map<String, ERIBLocale> locales = new HashMap<String, ERIBLocale>();

	/**
	 * Возвращает текстовку с дефолтной локалью с заданными ключом и бандлом
	 * @param key ключ
	 * @param bundle бандл
	 * @return текстовка
	 */
	public static String getMessage(String key, String bundle)
	{
		try
		{
			return getMessage(key, bundle, MultiLocaleContext.getLocaleId());
		}
		catch (SystemException e)
		{
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("ReuseOfLocalVariable")
	private static String getPrivateMessage(String key, String bundle, String localeId) throws SystemException
	{
		Application application = ApplicationInfo.getCurrentApplication();
		for(ERIBMessageKey messageKey: ERIBMessageKeyManager.getEribMessageKeyList(application, key, bundle, localeId))
		{
			ERIBLocale locale = getLocale(messageKey.getLocaleId());
			if (!locale.applicationAllowed(application))
				continue;
			ERIBLocaleMessageResources messageResources = getMessageResources(messageKey.getLocaleId());
			String message = messageResources.getMessage(messageKey.getKey(), messageKey.getBundle());
			if (message != null)
				return message;

			log.trace("Для  текстового элемента "+messageKey.getKey()+" не найдено значение на "+localeId+" языке.");
		}
		log.error("Для  текстового элемента "+key+" не найдено значение на "+localeId+" языке.");
		return null;
	}

	/**
	 * Возвращает текстовку с заданной локалью, ключом и бандлом
	 * @param key ключ
	 * @param bundle бандл
	 * @param localeId идентификатор локали
	 * @return текстовка
	 */
	public static String getMessage(String key, String bundle, String localeId) throws SystemException
	{
		ERIBLocale locale = LocaleHelper.getLocale(localeId);
		if (lastUpdateDate.get() == locale.getActualDate().getTimeInMillis() || isSynchStart)
		{
			return getPrivateMessage(key, bundle, localeId);
		}
		synchronized (LOCK)
		{
			if (lastUpdateDate.get() == locale.getActualDate().getTimeInMillis() || isSynchStart)
			{
				return getPrivateMessage(key, bundle, localeId);
			}
			isSynchStart = true;
		}
		try
		{
			autoRefresh();
			lastUpdateDate.set(locale.getActualDate().getTimeInMillis());
		}
		catch (Exception ex)
		{
			throw new SystemException("Ошибка при обновлении текстовок", ex);
		}
		finally
		{
			isSynchStart = false;
		}
		return getPrivateMessage(key, bundle, localeId);
	}

	/**
	 * @param updateLocaleMessage событие обновления
	 */
	public static void update(UpdateMessagesEvent updateLocaleMessage)
	{
		switch (updateLocaleMessage.getType())
		{
			case UPDATE_LOCALE:
				updateLocale(updateLocaleMessage.getLocale());
				break;
			case REMOVE_LOCALE:
				remove(updateLocaleMessage.getLocaleId());
				break;
			case UPDATE_MESSAGES:
				updateMessages(updateLocaleMessage.getLocaleId());
				break;
		}
	}

	private static void remove(String localeId)
	{
		removeLocale(localeId);
		removeMessages(localeId);
	}

	private static void removeMessages(String localeId)
	{
		synchronized (localeMessages)
		{
			localeMessages.remove(localeId);
		}
	}

	private static void removeLocale(String localeId)
	{
		synchronized (locales)
		{
			locales.remove(localeId);
		}
	}

	/**
	 * создать ивент
	 * @param type тип
	 * @param locale локаль
	 * @throws SystemException
	 */
	public static void createUpdateEvent(UpdateLocaleType type, ERIBLocale locale) throws SystemException
	{
		try
		{
			EventSender.getInstance().sendEvent(new UpdateMessagesEvent(type, locale));
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	private static ERIBLocale getLocale(String localeId) throws SystemException
	{
		ERIBLocale locale = locales.get(localeId);
		if (locale == null)
			synchronized (locales)
			{
				locale = locales.get(localeId);
				if (locale == null)
				{
					locale = localesService.getById(localeId, getInstanceName());
					locales.put(localeId, locale);
				}
			}

		return locale;
	}

	private static ERIBLocaleMessageResources getMessageResources(String localeId)
	{
		ERIBLocaleMessageResources messageResources = localeMessages.get(localeId);
		if (messageResources != null)
			return messageResources;
		synchronized (localeMessages)
		{
			//noinspection ReuseOfLocalVariable
			messageResources = localeMessages.get(localeId);
			if (messageResources != null)
				return messageResources;
			return initializeMessages(localeId);
		}
	}

	private static void updateMessages(String localeId)
	{
		synchronized (localeMessages)
		{
			initializeMessages(localeId);
		}
	}

	private static ERIBLocaleMessageResources initializeMessages(String localeId)
	{
		ERIBLocaleMessageResources messageResources = new ERIBLocaleMessageResources(localeId);
		messageResources.initialize();
		localeMessages.put(localeId, messageResources);
		return messageResources;
	}

	private static void updateLocale(ERIBLocale newLocale)
	{
		synchronized (locales)
		{
			locales.put(newLocale.getId(), newLocale);
		}
	}

	private static void autoRefresh() throws SystemException
	{
		List<ERIBLocale> idList = localesService.getAll(getInstanceName());
		for (ERIBLocale locale : idList)
		{
			updateLocale(locale);
			updateMessages(locale.getId());
		}
	}

	private static String getInstanceName()
	{
		return ConfigFactory.getConfig(ERIBLocaleConfig.class).getDbInstanceName();
	}
}
