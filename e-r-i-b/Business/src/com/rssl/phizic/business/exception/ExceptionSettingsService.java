package com.rssl.phizic.business.exception;

import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessagesHelper;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.*;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 26.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Сервис настройки функционала статистики исключений 
 */

public class ExceptionSettingsService extends Config
{
	private static final String ARCHIVE_PATH_PARAMETER = "com.rssl.iccs.report.exception.archiving.path";
	private static final String UPDATE_EXCEPTION_INFORMATION_KEY = "com.rssl.phizic.business.exception.updateExceptionInformation.enabled";
	private static final String CSAADMIN_DB_LINK_NAME = "com.rssl.iccs.dbserver.csaadmin.dblink.name";

	public static final String USE_ARCHIVING_PARAMETER = "com.rssl.iccs.report.exception.archiving.use";
	public static final String CLIENT_DEFAULT_MESSAGE_KEY = "exception.defaultMessages.client";
	public static final String ADMIN_DEFAULT_MESSAGE_KEY = "exception.defaultMessages.admin";

	private static final Map<Application,String> exceptionKeys = new HashMap<Application,String>();

	private boolean isUpdateExceptionInfoAvailable;
	private String csaAdminDbLinkName;

	static
	{
		exceptionKeys.put(Application.PhizIC,CLIENT_DEFAULT_MESSAGE_KEY);
		exceptionKeys.put(Application.PhizIA,ADMIN_DEFAULT_MESSAGE_KEY);
	}

	public ExceptionSettingsService(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return используется ли архивация отчетов по ошибкам
	 * @throws Exception
	 */
	public boolean isUseArchiving() throws Exception
	{
		return getBoolProperty(USE_ARCHIVING_PARAMETER);
	}

	/**
	 * @return путь к отчетам по ошибкам
	 */
	public String getArchivePath() throws Exception
	{
		return getProperty(ARCHIVE_PATH_PARAMETER);
	}

	/**
	 * Получение сообщения об ошибке для приложения.
	 * @param application - приложение
	 * @return текст об шибке
	 */
	public static String getDefaultMessageForApplication(Application application)
	{
		String exceptionKey = exceptionKeys.get(application);
		if(StringHelper.isNotEmpty(exceptionKey))
			return StaticMessagesHelper.findTextByKey(exceptionKey);
		return null;
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		isUpdateExceptionInfoAvailable = getBoolProperty(UPDATE_EXCEPTION_INFORMATION_KEY);
		csaAdminDbLinkName = getProperty(CSAADMIN_DB_LINK_NAME);
	}

	/**
	 * @return разрешено ли обновлять статистику исключений
	 */
	public boolean isUpdateExceptionInfoAvailable()
	{
		return isUpdateExceptionInfoAvailable;
	}

	/**
	 * @return линк на базу ЦСА Админ
	 */
	public String getCsaAdminDbLinkName()
	{
		return csaAdminDbLinkName;
	}
}
