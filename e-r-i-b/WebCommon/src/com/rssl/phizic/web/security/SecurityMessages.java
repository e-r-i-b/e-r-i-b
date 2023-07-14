package com.rssl.phizic.web.security;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.messaging.ext.sbrf.MobileBankNotAvailabeException;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.security.SecurityUtil;
import com.rssl.phizic.web.WebContext;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Evgrafov
 * @ created 03.01.2007
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */

public class SecurityMessages
{
	private static final String MOBILEBANK_BASIC_REGISTRATION_PATH = "/private/register-mobilebank/start.do";

	private static final String MOBILEBANK_EXTERNAL_PAYMENT_REGISTRATION_PATH = "/external/register-mobilebank/start.do";

	private static final String MOBILEBANK_LOGIN_REGISTRATION_PATH = "/login/register-mobilebank/start.do";

	/**
	 * Получить текстовое описание ошибки
	 *
	 * @param e исключение
	 * @return сообщение
	 *
	 */
	public static String translateException(Exception e)
	{
		@SuppressWarnings({"ThrowableResultOfMethodCallIgnored"})
		Throwable cause = ExceptionUtils.getRootCause(e);

		String message = null;
		if (cause instanceof MobileBankNotAvailabeException)
			message = formatMobileBankRegistrationOffer();

		if (message == null)
			message = e.getMessage();

		return message;
	}

	private static String formatMobileBankRegistrationOffer()
	{
		ApplicationConfig appConfig  = ApplicationConfig.getIt();

		if (Application.PhizIC == appConfig.getApplicationInfo().getApplication())
		{
			String offerMessage = "Для того чтобы получить одноразовый пароль по SMS, " +
					"Вам необходимо подключить услугу «Мобильный банк».";

			if(PermissionUtil.impliesService("MobileBankRegistration"))
			{
				// Формируем ссылку на страницу с подключением услуги "Мобильный Банк"
				// Ссылка включает адрес возврата на текущую страницу
				String link = getMobileBankRegistrationURL();
				offerMessage += " Для этого нажмите на ссылку " + link + ".";
			}

			return offerMessage;
		}
		return null;
	}

	private static String getMobileBankRegistrationURL()
	{
		HttpServletRequest request = WebContext.getCurrentRequest();
		if (!SecurityUtil.isAuthenticationComplete())
		{
			// Для страниц аутентификации адрес возврата не нужен
			String href = request.getContextPath() + MOBILEBANK_LOGIN_REGISTRATION_PATH;
			return String.format("<a href=\"%s\">Подключить</a>", href);
		}
		else
		{
			// Для остальных страниц адрес возврата берём из строки браузера

			AuthenticationContext authContext = AuthenticationContext.getContext();
			UserVisitingMode visitingMode = authContext.getVisitingMode();

			String href;
			switch (visitingMode)
			{
				case BASIC:
				default:
					href = request.getContextPath() + MOBILEBANK_BASIC_REGISTRATION_PATH;
					break;
				case PAYORDER_PAYMENT:
					href = request.getContextPath() + MOBILEBANK_EXTERNAL_PAYMENT_REGISTRATION_PATH;
					break;
			}
			String back = "encodeURIComponent(document.location.href)";
			return String.format("<a href=\"#\" onclick=\"goTo('%1$s?returnURL=' + %2$s);\">Подключить</a>", href, back);
		}
	}
}
