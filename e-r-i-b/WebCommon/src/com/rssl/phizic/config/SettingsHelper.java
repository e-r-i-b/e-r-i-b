package com.rssl.phizic.config;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.config.earlyloanrepayment.EarlyLoanRepaymentConfig;
import com.rssl.phizic.config.remoteConnectionUDBO.RemoteConnectionUDBOConfig;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.DepositConfig;

import java.text.ParseException;
import java.util.Calendar;

/**
 * ������ � ��������� �������� � jsp �� ��������
 * @author basharin
 * @ created 14.11.14
 * @ $Author$
 * @ $Revision$
 */

public class SettingsHelper
{
	/**
	 * ������������ �� ������ https://vojs.group-ib.ru/vaultonline-2.js ��� ����� ���������� �� ������ ��������
	 * @return ����������� �� ������
	 */
	public static boolean useVaultOnline()
	{
		return ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).isUseVaultOnline();
	}

	/**
	 * @return ����� ������� �� �������� �����
	 */
	public static String getLoginPageMessageSecure()
	{
		return ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getLoginPageMessageSecure();
	}

	/**
	 * @return ����� ����������� �� �������� �����
	 */
	public static String getLoginPageMessageSlide(int num, String key)
	{
		return ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getProperty(ApplicationAutoRefreshConfig.MESSAGE_LOGIN_SLIDE_KEY + num + "." + key);
	}

	/**
	 * @return ����� ��� �������� �������� � �������� ���������� �������� ����
	 */
	public static String getAcceptConnectUdboMessage()
	{
		return ConfigFactory.getConfig(RemoteConnectionUDBOConfig.class).getAcceptConnectUdboMessage();
	}

	/**
	 * ������������ �� ����������� ����������� ��� ��� ��� ������ �� ��������
	 * @return
	 */
	public static boolean isUseCasNsiDictionaries()
	{
		return ConfigFactory.getConfig(DepositConfig.class).isUseCasNsiDictionaries();
	}

	/**
	 * @return ��������� ���� ��������� ���������� ��� ���������� ��������� �������
	 */
	public static String getStarDateEarlyLoanRepayment()
	{
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_YEAR, ConfigFactory.getConfig(EarlyLoanRepaymentConfig.class).getMinDate());
		return DateHelper.getDateFormat(date.getTime());
	}

	/**
	 * @return �������� ���� ��������� ���������� ��� ���������� ��������� �������
	 */
	public static String getEndDateEarlyLoanRepayment(String loanTermEnd)
	{

		int addDays = ConfigFactory.getConfig(EarlyLoanRepaymentConfig.class).getMaxDate();
		if (addDays == 0)
			return loanTermEnd;

		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_YEAR, addDays);
		try
		{
			Calendar dateTermEnd = DateHelper.parseCalendar(loanTermEnd, "dd.MM.yyyy");
			return dateTermEnd.before(date) ? loanTermEnd : DateHelper.getDateFormat(date.getTime());
		}
		catch (ParseException e)
		{
			return DateHelper.getDateFormat(date.getTime());
		}
	}
}
