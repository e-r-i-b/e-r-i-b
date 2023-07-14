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
	 * Получение признака отображения предложений банка на главной странице
	 * @return true - отображать предложения, false - не отображать
	 */
	public static boolean isShowBankOffersOnMain()
	{
		try
		{
			return ConfigFactory.getConfig(UserPropertiesConfig.class).isBankOfferViewed();
		}
		catch (Exception e)
		{
			log.error("Ошибка получения профиля пользователя", e);
			return false;
		}
	}

	/**
	 * Получение признака доступности АЛФ
	 * @return true - доступен, false - недоступен.
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
			log.error("Ошибка получения профиля пользователя", e);
		}

		return false;
	}

	/**
	 * Получить профиль по логину
	 * @param login логин
	 * @return профиль
	 */
	public static Profile getProfileByLogin(CommonLogin login)
	{
		try
		{
			return service.findByLogin(login);
		}
		catch (Exception e)
		{
			log.error("Ошибка получения профиля пользователя", e);
		}
		return null;
	}

	/**
	 * Получить название поля для реквизитов корзины по ключу.
	 * @param key ключ
	 * @return название поля
	 */
	public static String getFieldNameForReq(String key)
	{
		ProfileConfig config = ConfigFactory.getConfig(ProfileConfig.class);
		return config.getFieldNameForReq(key);
	}
}
