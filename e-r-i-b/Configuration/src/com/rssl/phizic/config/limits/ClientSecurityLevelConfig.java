package com.rssl.phizic.config.limits;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.Date;

/**
 *  онфигураци€ настроек дл€ определени€ уровн€ безопасности клиента
 * @author niculichev
 * @ created 01.02.14
 * @ $Author$
 * @ $Revision$
 */
public class ClientSecurityLevelConfig extends Config
{
	public static final String CHANGE_PHONE_BY_ERIB_PROPERTY_KEY   = "com.rssl.phizic.business.limits.security.level.setting.isChangePhoneByERIB";
	public static final String CHANGE_PHONE_BY_CC_PROPERTY_KEY     = "com.rssl.phizic.business.limits.security.level.setting.isChangePhoneByCC";
	public static final String CHANGE_PHONE_BY_ATM_PROPERTY_KEY    = "com.rssl.phizic.business.limits.security.level.setting.isChangePhoneByATM";
	public static final String CHANGE_PHONE_BY_VSP_PROPERTY_KEY    = "com.rssl.phizic.business.limits.security.level.setting.isChangePhoneByVSP";
	public static final String LESS_COUNT_DAYS_PROPERTY_KEY        = "com.rssl.phizic.business.limits.security.level.setting.less.count.days";
	public static final String START_DATE_PROPERTY_KEY             = "com.rssl.phizic.business.limits.security.level.setting.start.date";

	// каналы, через которые был изменен телефон
	private boolean isChangePhoneByERIB;
	private boolean isChangePhoneByCC;
	private boolean isChangePhoneByATM;
	private boolean isChangePhoneByVSP;
	// количество дней
	private Integer lessCountDays;
	// дата начала действи€
	private Date startDate;

	public ClientSecurityLevelConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		isChangePhoneByERIB = getBoolProperty(CHANGE_PHONE_BY_ERIB_PROPERTY_KEY);
		isChangePhoneByCC = getBoolProperty(CHANGE_PHONE_BY_CC_PROPERTY_KEY);
		isChangePhoneByATM = getBoolProperty(CHANGE_PHONE_BY_ATM_PROPERTY_KEY);
		isChangePhoneByVSP = getBoolProperty(CHANGE_PHONE_BY_VSP_PROPERTY_KEY);

		try
		{
			String strLessCountDays = getProperty(LESS_COUNT_DAYS_PROPERTY_KEY);
			lessCountDays = StringHelper.isEmpty(strLessCountDays) ? null : Integer.valueOf(strLessCountDays);

			String strStartDate = getProperty(START_DATE_PROPERTY_KEY);
			startDate = StringHelper.isEmpty(strStartDate) ? null : DateHelper.parseStringTime(strStartDate);
		}
		catch (Exception e)
		{
			throw new ConfigurationException("Ќе удалось определить настройки уровн€ безопасности клиента.", e);
		}
	}

	public boolean isChangePhoneByERIB()
	{
		return isChangePhoneByERIB;
	}

	public  boolean isChangePhoneByCC()
	{
		return isChangePhoneByCC;
	}

	public boolean isChangePhoneByATM()
	{
		return isChangePhoneByATM;
	}

	public boolean isChangePhoneByVSP()
	{
		return isChangePhoneByVSP;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public Integer getLessCountDays()
	{
		return lessCountDays;
	}

	public static ClientSecurityLevelConfig getInstance()
	{
		return ConfigFactory.getConfig(ClientSecurityLevelConfig.class);
	}
}
