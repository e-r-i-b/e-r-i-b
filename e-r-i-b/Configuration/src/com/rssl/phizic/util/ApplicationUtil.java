package com.rssl.phizic.util;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ApplicationConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ��������� ����� ��� ����������
 * @author Dorzhinov
 * @ created 15.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class ApplicationUtil
{
	/**
	 * �������� �� ���������� mAPI?
	 * ���������� ������������ ApplicationInfo
	 * @return true - mAPI
	 */
	public static boolean isMobileApi()
	{
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		return applicationConfig.getApplicationInfo().isMobileApi();
	}

	/**
	 * �������� �� �� mAPI
	 * @return true - �� mAPI
	 */
	public static boolean isNotMobileApi()
	{
		return !isMobileApi();
	}

	/**
	 * �������� �� ���������� mAPI ��� atmAPI ��� socialApi
	 * ���������� ������������ ApplicationInfo
	 * @return true - mAPI ��� atmAPI
	 */
	public static boolean isApi()
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		return applicationInfo.isMobileApi() || applicationInfo.isATM() || applicationInfo.isSocialApi();
	}

    /**
     * �������� �� ���������� socialApi
     * ���������� ������������ ApplicationInfo
     * @return true - socialApi
     */
    public static boolean isSocialApi()
    {
        ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
        return applicationInfo.isSocialApi();
    }

	/**
	 * �������� �� �� API
	 * @return true - �� mAPI � �� atmAPI
	 */
	public static boolean isNotApi()
	{
		return !isApi();
	}

	/**
	 * �������� �� ���������� atmAPI?
	 * @return true - atmAPI
	 */
	public static boolean isATMApi()
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		return applicationInfo.isATM();
	}

	/**
	 * @return true - ���� ���������� "���-����� ����"
	 */
	public static boolean isErmbSms()
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		return applicationInfo.getApplication() == Application.ErmbSmsChannel;
	}

	/**
	 * ���������� ��������������� �� ����������� ������ ������ API
	 * @return ������ ������ API (1, 2, 3 � �.�.)
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
     * ���������� ��������������� ������ ���� ���������� API
     * @return ������ �������� ���������� API ("mobile", "mobile2", "mobile3", ...)
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
     * ���������� ������ ���������� API
     * @return ������ ���������� API ("mobile", "mobile2", "mobile3", ...)
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
	 * �������� �� ���������� WebAPI
	 * ���������� ������������ ApplicationInfo
	 * @return true - WebAPI
	 */
	public static boolean isWebAPI()
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		return applicationInfo.isWebApi();
	}

	/**
	 * �������� �� ���������� PhizIC
	 * @return ��, ���� ��������. ��� � ��������� ������.
	 */
	public static boolean isPhizIC(){
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		return applicationInfo.isPhizIC();
	}

	/**
	 * �������� �� ���������� ���������
	 * @return true = PhizIA || CSAAdmin
	 */
	public static boolean isAdminApplication()
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		return applicationInfo.isAdminApplication();
	}

	/**
	 * �������� �� ���������� Scheduler
	 * @return ��, ���� ��������.
	 */
	public static boolean isScheduler()
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		return applicationInfo.isScheduler();
	}

	/**
	 * �������� �� ���������� ����������
	 * @return true - ��, ���� ��������.
	 */
	public static boolean isClientApplication()
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		return applicationInfo.isClientApplication();
	}
}
