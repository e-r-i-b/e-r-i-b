package com.rssl.phizic.util;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ApplicationConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ”тилитный класс дл€ приложений
 * @author Dorzhinov
 * @ created 15.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class ApplicationUtil
{
	/**
	 * явл€етс€ ли приложение mAPI?
	 * ‘актически используетс€ ApplicationInfo
	 * @return true - mAPI
	 */
	public static boolean isMobileApi()
	{
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		return applicationConfig.getApplicationInfo().isMobileApi();
	}

	/**
	 * ѕроверка на Ќ≈ mAPI
	 * @return true - не mAPI
	 */
	public static boolean isNotMobileApi()
	{
		return !isMobileApi();
	}

	/**
	 * явл€етс€ ли приложение mAPI или atmAPI или socialApi
	 * ‘актически используетс€ ApplicationInfo
	 * @return true - mAPI или atmAPI
	 */
	public static boolean isApi()
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		return applicationInfo.isMobileApi() || applicationInfo.isATM() || applicationInfo.isSocialApi();
	}

    /**
     * явл€етс€ ли приложение socialApi
     * ‘актически используетс€ ApplicationInfo
     * @return true - socialApi
     */
    public static boolean isSocialApi()
    {
        ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
        return applicationInfo.isSocialApi();
    }

	/**
	 * ѕроверка на Ќ≈ API
	 * @return true - не mAPI и не atmAPI
	 */
	public static boolean isNotApi()
	{
		return !isApi();
	}

	/**
	 * явл€етс€ ли приложение atmAPI?
	 * @return true - atmAPI
	 */
	public static boolean isATMApi()
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		return applicationInfo.isATM();
	}

	/**
	 * @return true - если приложение "смс-канал ≈–ћЅ"
	 */
	public static boolean isErmbSms()
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		return applicationInfo.getApplication() == Application.ErmbSmsChannel;
	}

	/**
	 * возвращает отсортированный по возрастанию список версий API
	 * @return список версий API (1, 2, 3 и т.д.)
	 */
	public static List<Integer> getAllSortApiVersions()
	{
		List<Integer> versions = new ArrayList<Integer>();

		Application[] applications = Application.values();
		for (Application application : applications)
		{
			Integer version = new ApplicationInfo(application).getApiMajorVersion();
			if (version != null)
                versions.add(version);
		}

		Collections.sort(versions);
		return versions;
	}

	/**
     * ¬озвращает отсортированный список имен приложений API
     * @return список названий приложений API ("mobile", "mobile2", "mobile3", ...)
     */
    public static List<String> getMobileApiNameList()
    {
        List<String> names = new ArrayList<String>();

        Application[] applications = Application.values();
		for (Application application : applications)
			if (new ApplicationInfo(application).isMobileApi())
                names.add(application.name());

        Collections.sort(names);
        return names;
    }

	/**
     * ¬озвращает список приложений API
     * @return список приложений API ("mobile", "mobile2", "mobile3", ...)
     */
    public static List<Application> getMobileApiApplicationList()
    {
        List<Application> apiList = new ArrayList<Application>();

	    for (Application application : Application.values())
			if (new ApplicationInfo(application).isMobileApi())
                apiList.add(application);

        return apiList;
    }

	/**
	 * явл€етс€ ли приложение WebAPI
	 * ‘актически используетс€ ApplicationInfo
	 * @return true - WebAPI
	 */
	public static boolean isWebAPI()
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		return applicationInfo.isWebApi();
	}

	/**
	 * явл€етс€ ли приложение PhizIC
	 * @return да, если €вл€етс€. Ќет в противном случае.
	 */
	public static boolean isPhizIC(){
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		return applicationInfo.isPhizIC();
	}

	/**
	 * явл€етс€ ли приложение админским
	 * @return true = PhizIA || CSAAdmin
	 */
	public static boolean isAdminApplication()
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		return applicationInfo.isAdminApplication();
	}

	/**
	 * явл€етс€ ли приложение Scheduler
	 * @return да, если €вл€етс€.
	 */
	public static boolean isScheduler()
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		return applicationInfo.isScheduler();
	}

	/**
	 * явл€етс€ ли приложение клиентским
	 * @return true - да, если €вл€етс€.
	 */
	public static boolean isClientApplication()
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		return applicationInfo.isClientApplication();
	}
}
