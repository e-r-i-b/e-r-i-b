package com.rssl.phizic.common.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ���������� � ����������
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
	 * ���� ����������� ������������� ��� ������� ���� ������ ����������, �������� �� ���������� ������������ � ApplicationInfo.
	 */
	private static final ThreadLocal<Application> moduleNameToThread = new ThreadLocal<Application>();

	/**
	 * ������������� ���������� �������� �� ���������� �� ��������� ��� ������� ����.
	 *
	 * @param application ����������.
	 */
	public static void setCurrentApplication(Application application)
	{
		moduleNameToThread.set(application);
	}

	/**
	 * ������������� ���������� �� ���������.
	 */
	public static void setDefaultApplication()
	{
		moduleNameToThread.remove();
	}

	/**
	 * @return �������� �������� ����������.
	 */
	public static Application getCurrentApplication()
	{
		if (moduleNameToThread.get() == null)
			throw new RuntimeException("�� ����������� �������� ���������� � ������� ������. ���������� ��������� setCurrentApplication");

		return moduleNameToThread.get();
	}

	/**
	 * @return ��������� �� ������� ���������� � ���� ������.
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
	 * @return ����������.
	 */
	public Application getApplication()
	{
		if (application == null)
			return getCurrentApplication();
		return application;
	}

    /**
	 * �������� �� ���������� ���� API
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
	 * @return �������� ���������� ���
	 */
	public boolean isATM()
	{
		return getApplication() == Application.atm;
	}

	/**
	 * @return �������� �� ���������� ��� ������� ����
	 */
	public boolean isSMS()
	{
		return getApplication() == Application.ErmbSmsChannel;
	}

	/**
	 * @return �������� �� ���������� ������� ���������� ����� ��� ������� �����
	 * (�������� ����� �������� ������)
	 */
	public boolean isWeb()
	{
		return getApplication() == Application.PhizIA || getApplication() == Application.PhizIC || getApplication() == Application.WebAPI;
	}

    /**
     * @return �������� ���������� ����������
     */
    public boolean isSocialApi()
    {
        return getApplication() == Application.socialApi;
    }

	/**
	 * @return �������� �� ����������
	 * (�������� ����� �������� ������)
	 */
	public boolean isClientApplication()
	{
		return getApplication() == Application.PhizIC || isSMS() || isATM() || isMobileApi() || isWebApi();
	}

	/**
     * @return true - ���������� �� �������� API
     */
    public boolean isNotMobileApi()
    {
        return !isMobileApi();
    }

    /**
	 * �������� ������ API �� �������� ����������
     * ������ ����� ������ ��� ��������� ������ API ������������� ������������ com.rssl.phizic.security.util.MobileApiUtil#getApiVersionNumber(),
     * �� ����������� ������� ����� ������ ����� ��� ��������� ��������������
	 * @return �������� ������ API ����������, ���� null (���� ��� �� API ������)
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
	 * @return ���������� ��������������.
	 */
	public boolean isAdminApplication()
	{
		return getApplication() == Application.PhizIA || getApplication() == Application.CSAAdmin;
	}

	/**
	 * @return �������� ����������� WebAPI
	 */
	public boolean isWebApi()
	{
		return getApplication() == Application.WebAPI;
	}

	/**
	 * ��������, �������� �� ���������� PhizIC
	 * @return ��, ���� ��������. ��� � ��������� ������
	 */
	public boolean isPhizIC()
	{
		return getApplication() == Application.PhizIC;
	}

	/**
	 * ��������, �������� �� ���������� Scheduler
	 * @return
	 */
	public boolean isScheduler()
	{
		return getApplication() == Application.Scheduler;
	}

	/**
	 * @return ��� ���
	 */
	public boolean isCSA()
	{
		Application app = getApplication();
		return Application.CSA == app
				|| Application.CSAAdmin == app
					|| Application.CSAFront == app;
	}

	/**
	 * @return �������� �� ������� ���������� ������������ ���->����
	 */
	public boolean isMbkErmbMigration()
	{
		Application app = getApplication();
		return app == Application.ERMBListMigrator
				|| app == Application.ASFilialListener
				|| app == Application.ErmbMbkListener;
	}
}
