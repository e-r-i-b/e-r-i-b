package com.rssl.phizic.business.profile;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.profile.ProfileConfig;
import com.rssl.phizic.userSettings.UserPropertiesConfig;


/**
 * @author gulov
 * @ created 30.06.2011
 * @ $Authors$
 * @ $Revision$
 */
public class ProfileUtils
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static ProfileService service = new ProfileService();

	/**
	 * ��������� �������� ����������� ����������� ����� �� ������� ��������
	 * @return true - ���������� �����������, false - �� ����������
	 */
	public static boolean isShowBankOffersOnMain()
	{
		try
		{
			return ConfigFactory.getConfig(UserPropertiesConfig.class).isBankOfferViewed();
		}
		catch (Exception e)
		{
			log.error("������ ��������� ������� ������������", e);
			return false;
		}
	}

	/**
	 * ��������� �������� ����������� ���
	 * @return true - ��������, false - ����������.
	 */
	public static boolean isPersonalFinanceEnabled()
	{
		try
		{
			if (!PersonContext.isAvailable())
			{
				return false;
			}

			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			if (personData.isGuest())
			{
				return false;
			}

			Profile profile = personData.getProfile();
			return profile.isShowPersonalFinance();
		}
		catch (Exception e)
		{
			log.error("������ ��������� ������� ������������", e);
		}

		return false;
	}

	/**
	 * �������� ������� �� ������
	 * @param login �����
	 * @return �������
	 */
	public static Profile getProfileByLogin(CommonLogin login)
	{
		try
		{
			return service.findByLogin(login);
		}
		catch (Exception e)
		{
			log.error("������ ��������� ������� ������������", e);
		}
		return null;
	}

	/**
	 * �������� �������� ���� ��� ���������� ������� �� �����.
	 * @param key ����
	 * @return �������� ����
	 */
	public static String getFieldNameForReq(String key)
	{
		ProfileConfig config = ConfigFactory.getConfig(ProfileConfig.class);
		return config.getFieldNameForReq(key);
	}
}
