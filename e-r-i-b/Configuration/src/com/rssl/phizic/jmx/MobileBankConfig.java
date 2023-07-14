package com.rssl.phizic.jmx;

import com.rssl.phizic.config.*;

/**
 * Created by IntelliJ IDEA.
 * User: Moshenko
 * Date: 06.09.2012
 * Time: 15:58:54
 */
public class MobileBankConfig extends Config implements MobileBankConfigMBean
{
	public static final String REGISTRATION_REPEAT_INTERVAL = "mb.registration.repeat.interval";
	public static final String MB_SYSTEM_ID = "mb.system.id";
	private static final String CONFIRM_REGISTRATION_LOADING_MAX_VALUE = "mb.сonfirm.registration.loading.max.value";
	private static final String MB_REG_LOAD_ENABLED = "mk.registration.load.enabled";
	private static final String ERMB_MANAGE_PHONES_ENABLED = "ermb.manage.phones.enabled";
	private static final String ERMB_PHONES_MAX_COUNT = "ermb.manage.phones.max.count";

	private long regRepeatInterval; //интервал повторного запуска процедуры подключения услуги Мобильный банк.(в минутах)
	private String dataSourceName;
	private String dataSourceReportName;
	private boolean useWay4uDirect;
	private int  confirmRegMaxValue;
	private boolean  mbkRegistrationLoadEnabled;
	private int ermbPhonesMaxCount;
	private boolean ermbManagePhonesEnabled;

	public MobileBankConfig(PropertyReader reader)
	{
		super(reader);
	}

	public long getRegRepeatInterval()
	{
		return regRepeatInterval;
	}

	public String getDataSourceName()
	{
		return dataSourceName;
	}

	public String getDataSourceReportName()
	{
		return dataSourceReportName;
	}

	public void doRefresh() throws ConfigurationException
	{
        regRepeatInterval = Long.valueOf(getProperty(REGISTRATION_REPEAT_INTERVAL));
		dataSourceName = getProperty("datasource.name");
		dataSourceReportName = getProperty("datasource.report.name");
		useWay4uDirect = getBoolProperty("com.rssl.gate.mobilebank.use-way4u-directly");
		mbkRegistrationLoadEnabled = getBoolProperty(MB_REG_LOAD_ENABLED);
		try
		{
			confirmRegMaxValue = Integer.valueOf(getProperty(CONFIRM_REGISTRATION_LOADING_MAX_VALUE));
		}
		catch (NumberFormatException ignored) {}

		ermbPhonesMaxCount = getIntProperty(ERMB_PHONES_MAX_COUNT);
		ermbManagePhonesEnabled = getBoolProperty(ERMB_MANAGE_PHONES_ENABLED);
	}

	public boolean isUseWay4uDirect()
	{
		return useWay4uDirect;
	}

	/**
	 * @return Максимальный размер пачки в mb_BATCH_ERMB_ConfirmRegistrationLoading
	 */
	public int getConfirmRegMaxValue()
	{
		return confirmRegMaxValue;
	}

	public void setConfirmRegMaxValue(int confirmRegMaxValue)
	{
		this.confirmRegMaxValue = confirmRegMaxValue;
	}

	/**
	 * @return Включена ли загрузка регистраций МБК
	 */
	public boolean isMbkRegistrationLoadEnabled()
	{
		return mbkRegistrationLoadEnabled;
	}

	public void setMbkRegistrationLoadEnabled(boolean mbkRegistrationLoadEnabled)
	{
		this.mbkRegistrationLoadEnabled = mbkRegistrationLoadEnabled;
	}

	public int getErmbPhonesMaxCount()
	{
		 return ermbPhonesMaxCount;
	}

	public boolean isErmbManagePhonesEnabled()
	{
		return ermbManagePhonesEnabled;
	}
}
