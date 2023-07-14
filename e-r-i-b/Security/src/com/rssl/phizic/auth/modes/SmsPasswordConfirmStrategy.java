package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.readers.SmsPasswordConfirmResponseReader;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.logging.confirm.ConfirmType;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.password.OneTimePassword;

/**
 * @author eMakarov
 * @ created 17.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class SmsPasswordConfirmStrategy extends OneTimePasswordConfirmStrategy
{
	public static final String PASSWORD_KEY = SmsPasswordConfirmStrategy.class.getName() + ".sms.password";
	public static final String NAME_BUTTON_NEW = "\"Ќовый SMS-пароль\"";

	public SmsPasswordConfirmStrategy() // без этого не работает :( падает ClientInitializerListener
	{
	}

	protected Class<SmsPasswordConfirmRequest> getPasswordRequestClass()
	{
		return SmsPasswordConfirmRequest.class;
	}

	public ConfirmStrategyType getType()
	{
		return ConfirmStrategyType.sms;
	}

	protected ConfirmType getConfirmType()
	{
		return ConfirmType.SMS;
	}

	protected String getPasswordKey()
	{
		return PASSWORD_KEY;
	}

	protected String getNameButton()
	{
		return NAME_BUTTON_NEW;
	}

	/**
	 * —оздать запрос на подтверждение  (если requireValues() == true)
	 * @param login логин дл€ которого создаетс€ запрос
	 * @param value значение дл€ запроса
	 * @param sessionId идентификатор текущей сессии
	 * @return запрос
	 * @throws
	 */
	public ConfirmRequest createRequest(CommonLogin login, ConfirmableObject value, String sessionId, PreConfirmObject preConfirm) throws SecurityException
	{
		return new SmsPasswordConfirmRequest(value);
	}

	public ConfirmResponseReader getConfirmResponseReader()
	{
		return new SmsPasswordConfirmResponseReader();
	}

	/**
	 * ѕолучение "живого" парол€, т.е. нового (не использованного и не заблокированного)
	 * @param login - логин пользовател€
	 * @param confirmableObject - объект подтверждени€
	 * @return јктивный смс-пароль или null
	 * @throws SecurityDbException
	 * @deprecated не использовать. оставлено из-за устаревщих использований в мапи.
	 */
	@Deprecated
	public static OneTimePassword getSmsPassword(Login login, ConfirmableObject confirmableObject) throws SecurityDbException
	{
		if (confirmableObject == null)
		{
			return null;
		}
		return restorePassword(confirmableObject, PASSWORD_KEY);
	}
}
