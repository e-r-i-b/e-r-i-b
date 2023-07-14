package com.rssl.phizic.business.exception.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.locale.ERIBLocaleConfig;
import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.gate.statistics.exception.ExternalExceptionInfo;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author akrenev
 * @ created 31.10.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор локалезависимых сообщений об ошибке внешней системы
 */

public final class ExternalLocalizingExceptionProcessor
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final Object CONTAINER_LOCKER = new Object();
	private static volatile ExternalExceptionMappingContainer container = null;

	private static void initializeNewContainer()
	{
		ExternalExceptionMappingContainer localedContainer = new ExternalExceptionMappingContainer();
		localedContainer.initialize();
		container = localedContainer;
	}

	private static ExternalExceptionMappingContainer getContainer()
	{
		if (container == null)
		{
			synchronized (CONTAINER_LOCKER)
			{
				if (container == null)
				{
					initializeNewContainer();
				}
			}
		}

		if (container.isActual())
			return container;

		synchronized (CONTAINER_LOCKER)
		{
			if (container.isActual())
				return container;

			container.startRefresh();
		}

		initializeNewContainer();
		return container;
	}

	/**
	 * получить локалезависимую текстовку для ошибки внешней системы
	 * @param exceptionInfo информация об ошибке
	 * @return локализованное сообщение
	 */
	public static final String getMessage(ExternalExceptionInfo exceptionInfo)
	{
		if (!ConfigFactory.getConfig(ERIBLocaleConfig.class).isUseLocaledExternalSystemErrorMessages() || MultiLocaleContext.isDefaultLocale() || exceptionInfo.getGate() != System.esb)
			return null;

		return getContainer().getMessage(exceptionInfo.getMessageKey(), exceptionInfo.getErrorCode(), exceptionInfo.getErrorText());
	}

	private static class ExternalExceptionMappingContainer
	{
		private static final int LIFE_TIME = 15 * DateHelper.MILLISECONDS_IN_MINUTE;
		private static final LocalizingExceptionMappingService service = new LocalizingExceptionMappingService();

		private Calendar lastUpdateTime = Calendar.getInstance();
		private boolean refreshStarted = false;

		private final Map<String, List<Mapping>> mappings = new HashMap<String, List<Mapping>>();

		private void startRefresh()
		{
			this.refreshStarted = true;
		}

		private boolean isActual()
		{
			return refreshStarted || DateHelper.diff(Calendar.getInstance(), lastUpdateTime) < LIFE_TIME;
		}

		private String getKey(String messageKey, String errorCode)
		{
			return messageKey + "^" + errorCode;
		}

		private List<LocalizingExceptionMapping> getSource()
		{
			try
			{
				return service.getAll();
			}
			catch (BusinessException e)
			{
				log.error("Ошибка получения локалезависимых маппируемых сообщений.", e);
				return Collections.emptyList();
			}
		}

		private void initialize()
		{
			for (LocalizingExceptionMapping mapping : getSource())
			{
				String key = getKey(mapping.getMessageKey(), mapping.getErrorKey());
				List<Mapping> mappingList = mappings.get(key);
				if (mappingList == null)
				{
					mappingList = new ArrayList<Mapping>();
					mappings.put(key, mappingList);
				}

				mappingList.add(new Mapping(Pattern.compile(mapping.getPattern()), mapping.getFormatter()));
			}

		}

		private String getMessage(String messageKey, String errorCode, String errorMessage)
		{
			List<Mapping> mappingList = mappings.get(getKey(messageKey, errorCode));
			if (CollectionUtils.isEmpty(mappingList))
				return null;

			Iterator<Mapping> iterator = mappingList.iterator();
			String localizingMessage = null;
			while (iterator.hasNext() && StringHelper.isEmpty(localizingMessage))
				localizingMessage = iterator.next().process(errorMessage);

			return localizingMessage;
		}
	}

	private static class Mapping
	{
		private final Pattern pattern;
		private final String formatter;

		private Mapping(Pattern pattern, String formatter)
		{
			this.pattern = pattern;
			this.formatter = formatter;
		}

		public String process(String errorMessage)
		{
			Matcher matcher = pattern.matcher(errorMessage);
			if (!matcher.find())
				return null;

			String[] args = new String[matcher.groupCount()];
			for (int groupIndex = 1; groupIndex <= matcher.groupCount(); groupIndex++)
			{
				args[groupIndex - 1] = matcher.group(groupIndex);
			}
			return String.format(formatter, args);
		}
	}
}
