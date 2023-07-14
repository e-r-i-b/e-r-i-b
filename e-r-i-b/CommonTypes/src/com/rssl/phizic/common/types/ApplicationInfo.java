package com.rssl.phizic.common.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Информация о приложении
 * @author Dorzhinov
 * @ created 02.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class ApplicationInfo
{
    private static final Pattern PATTERN = Pattern.compile("^.*(\\d+)$");

    private final Application application;

	/**
	 * Есть возможность устанавливать для текущей нити другое приложение, отличное от приложения прописанного в ApplicationInfo.
	 */
	private static final ThreadLocal<Application> moduleNameToThread = new ThreadLocal<Application>();

	/**
	 * Устанавливает приложение отличное от приложения по умолчанию для текущей нити.
	 *
	 * @param application приложение.
	 */
	public static void setCurrentApplication(Application application)
	{
		moduleNameToThread.set(application);
	}

	/**
	 * Устанавливает приложение по умолчанию.
	 */
	public static void setDefaultApplication()
	{
		moduleNameToThread.remove();
	}

	/**
	 * @return название текущего приложения.
	 */
	public static Application getCurrentApplication()
	{
		if (moduleNameToThread.get() == null)
			throw new RuntimeException("Не установлено название приложения в текущем потоке. Необходимо выполнить setCurrentApplication");

		return moduleNameToThread.get();
	}

	/**
	 * @return определно ли текущее приложение в этом потоке.
	 */
	public static boolean isDefinedCurrentApplication()
	{
		return moduleNameToThread.get() != null;
	}

	public ApplicationInfo()
    {
	    application = null;
    }

    public ApplicationInfo(Application application)
    {
        this.application = application;
    }

	/**
	 * @return приложение.
	 */
	public Application getApplication()
	{
		if (application == null)
			return getCurrentApplication();
		return application;
	}

    /**
	 * Является ли приложение ЕРИБ API
	 * @return true - API
	 */
    public boolean isMobileApi()
	{
		return getApplication() == Application.mobile9
				||getApplication() == Application.mobile8
				|| getApplication() == Application.mobile7
                || getApplication() == Application.mobile6
                || getApplication() == Application.mobile5;
	}

	public static boolean isMobileApi(Application application)
	{
		return application == Application.mobile9
				||application == Application.mobile8
				|| application == Application.mobile7
                || application == Application.mobile6
                || application == Application.mobile5;
	}

	/**
	 * @return является приложение АТМ
	 */
	public boolean isATM()
	{
		return getApplication() == Application.atm;
	}

	/**
	 * @return является ли приложение СМС каналом ЕРМБ
	 */
	public boolean isSMS()
	{
		return getApplication() == Application.ErmbSmsChannel;
	}

	/**
	 * @return является ли приложение каналом сотрудника банка или клиента банка
	 * (основной канал интернет клиент)
	 */
	public boolean isWeb()
	{
		return getApplication() == Application.PhizIA || getApplication() == Application.PhizIC || getApplication() == Application.WebAPI;
	}

    /**
     * @return является приложение социальным
     */
    public boolean isSocialApi()
    {
        return getApplication() == Application.socialApi;
    }

	/**
	 * @return является ли приложение
	 * (основной канал интернет клиент)
	 */
	public boolean isClientApplication()
	{
		return getApplication() == Application.PhizIC || isSMS() || isATM() || isMobileApi() || isWebApi();
	}

	/**
     * @return true - Приложение не является API
     */
    public boolean isNotMobileApi()
    {
        return !isMobileApi();
    }

    /**
	 * отделяет версию API от названия приложения
     * Вместо этого метода для получения версии API рекомендуется использовать com.rssl.phizic.security.util.MobileApiUtil#getApiVersionNumber(),
     * за исключением случаев когда версия нужна вне контекста аутентификации
	 * @return мажорную версию API приложения, либо null (если это не API версия)
	 */
	public Integer getApiMajorVersion()
	{
		if (isMobileApi())
		{
			Matcher matcher = PATTERN.matcher(getApplication().name());
			if (!matcher.matches())
				return null;

			return Integer.parseInt(matcher.group(1));
		}
		return null;
	}

	/**
	 * @return приложение администратора.
	 */
	public boolean isAdminApplication()
	{
		return getApplication() == Application.PhizIA || getApplication() == Application.CSAAdmin;
	}

	/**
	 * @return является приложением WebAPI
	 */
	public boolean isWebApi()
	{
		return getApplication() == Application.WebAPI;
	}

	/**
	 * Проверка, является ли приложение PhizIC
	 * @return Да, если является. Нет в противном случае
	 */
	public boolean isPhizIC()
	{
		return getApplication() == Application.PhizIC;
	}

	/**
	 * Проверка, является ли приложение Scheduler
	 * @return
	 */
	public boolean isScheduler()
	{
		return getApplication() == Application.Scheduler;
	}

	/**
	 * @return это ЦСА
	 */
	public boolean isCSA()
	{
		Application app = getApplication();
		return Application.CSA == app
				|| Application.CSAAdmin == app
					|| Application.CSAFront == app;
	}

	/**
	 * @return является ли текущее приложение миграционным МБК->ЕРМБ
	 */
	public boolean isMbkErmbMigration()
	{
		Application app = getApplication();
		return app == Application.ERMBListMigrator
				|| app == Application.ASFilialListener
				|| app == Application.ErmbMbkListener;
	}
}
