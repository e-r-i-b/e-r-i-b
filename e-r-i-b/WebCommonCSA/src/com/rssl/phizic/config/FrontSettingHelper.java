package com.rssl.phizic.config;

import com.rssl.auth.csa.front.operations.auth.GlobalLoginRestriction;
import com.rssl.auth.csa.front.operations.auth.RecoverPasswordRestriction;
import com.rssl.auth.csa.front.operations.auth.RegistrationRestriction;
import com.rssl.auth.csa.front.operations.auth.verification.VerificationState;
import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.web.auth.payOrder.PayOrderHelper;

import static com.rssl.phizic.einvoicing.EInvoicingConstants.UEC_PAY_INFO;

/**
 * Хелпер для получения настроек фронта
 * @author niculichev
 * @ created 15.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class FrontSettingHelper
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB);

	private static final GlobalLoginRestriction globalLoginRestriction          = new GlobalLoginRestriction();
	private static final RecoverPasswordRestriction recoverPasswordRestriction  = new RecoverPasswordRestriction();
	private static final RegistrationRestriction registrationRestriction        = new RegistrationRestriction();

	/**
	 * Доступность самостоятельной регистрации по ссылке для своего сайта
	 * @return true - самостоятельная регистрация доступна
	 */
	public static boolean isAccessInternalRegistration()
	{
		try
		{
			return registrationRestriction.check();
		}
		catch (Exception e)
		{
			log.error("Ошибка получения настройки доступности самостоятельной регистрации", e);
			return false;
		}
	}

	/**
	 * Доступность самостоятельной регистрации по ссылке для чужого сайта
	 * @return true - самостоятельная регистрация доступна
	 */
	public static boolean isAccessExternalRegistration()
	{
		try
		{
			CSAFrontConfig config = ConfigFactory.getConfig(CSAFrontConfig.class);
			return config.isAccessExternalRegistration();
		}
		catch (Exception e)
		{
			log.error("Ошибка получения настройки доступности самостоятельной регистрации", e);
			return false;
		}
	}

	/**
	 * Доступность самостоятельного восстановления пароля
	 * @return true - восстановление пароля доступно
	 */
	public static boolean isAccessRecoverPassword()
	{
		try
		{
			return recoverPasswordRestriction.check();
		}
		catch (Exception e)
		{
			log.error("Ошибка получения настройки доступности самостоятельного восстановления пароля", e);
			return false;
		}
	}

	/**
	 * @return текст заголовока для формы ввода логин-пароля
	 */
	public static String getAuthTitle()
	{
		try
		{
			if (UEC_PAY_INFO.equals(PayOrderHelper.getPayOrderMode()))
				return "Подтверждение операции";
			return "Вход в Сбербанк Онлайн";
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * @return путь к пиктограмме в заголовоке формы ввода логин-пароля относительно <skinUrl>
	 */
	public static String getLoginAuthImage()
	{
		try
		{
			if (UEC_PAY_INFO.equals(PayOrderHelper.getPayOrderMode()))
				return "/skins/sbrf/images/csa/uec.jpg";
			return "/skins/sbrf/images/csa/lock.jpg";
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Глобальное ограничение входа
	 * @return true - вход для всех ограничен
	 */
	public static boolean isGlobalLoginRestriction()
	{
		try
		{
			return !globalLoginRestriction.check();
		}
		catch (Exception e)
		{
			log.error("Ошибка проверки глобального ограничения входа", e);
			return false;
		}

	}

	/**
	 * Доступна ли регистрация для пользовтаеля
	 * @param connectorInfo информация о коннекторе пользователя, может отсутствовать.
	 * @return да/нет
	 */
	public static boolean isRegistrationAllowed(ConnectorInfo connectorInfo)
	{
		if (connectorInfo == null)
		{
			return isAccessInternalRegistration();
		}
		CSAFrontConfig frontConfig = ConfigFactory.getConfig(CSAFrontConfig.class);
		return isAccessInternalRegistration() && !frontConfig.getOldCSACbCodesPattern().matcher(connectorInfo.getCbCode()).matches();
	}

	/**
	 * @return true -- использовать функционал верификации данных для деловой среды
	 */
	public static boolean isAccessToBusinessEnvironment()
	{
		CSAFrontConfig frontConfig = ConfigFactory.getConfig(CSAFrontConfig.class);
		return frontConfig.isAccessToBusinessEnvironment();
	}

	/**
	 * @return урл сайта деловая среда
	 */
	public static String getBusinessEnvironmentMainURL()
	{
		CSAFrontConfig frontConfig = ConfigFactory.getConfig(CSAFrontConfig.class);
		return frontConfig.getBusinessEnvironmentMainURL();
	}

	/**
	 * @return урл страницы клиента в деловой среде
	 */
	public static String getBusinessEnvironmentUserURL()
	{
		CSAFrontConfig frontConfig = ConfigFactory.getConfig(CSAFrontConfig.class);
		return frontConfig.getBusinessEnvironmentUserURL();
	}

	/**
	 * @param state результат верификации
	 * @return урл страницы после верификации
	 */
	public static String getBusinessEnvironmentAfterVerifyURL(VerificationState state)
	{
		CSAFrontConfig frontConfig = ConfigFactory.getConfig(CSAFrontConfig.class);
		return frontConfig.getBusinessEnvironmentVerifyStateURL(state);
	}

	/**
	 * @return Доступный тип регистрации(отдельной страницей или всплывающими окнами)
	 */
	public static RegistrationAccessType getRegistrationAccessType()
	{
		CSAFrontConfig frontConfig = ConfigFactory.getConfig(CSAFrontConfig.class);
		return frontConfig.getRegistrationAccessType();
	}

	public static boolean getAsyncCheckingFieldsIsEnabled()
	{
		CSAFrontConfig frontConfig = ConfigFactory.getConfig(CSAFrontConfig.class);
		return frontConfig.getAsyncCheckingFieldsIsEnabled();
	}

	/**
	 * @return true - если в CSAFront доступен DMP-Pixel, false - иначе
	 */
	public static boolean getPixelMetricActivity()
	{
		CSAFrontConfig frontConfig = ConfigFactory.getConfig(CSAFrontConfig.class);
		return frontConfig.isPixelMetricActivity();
	}
}
