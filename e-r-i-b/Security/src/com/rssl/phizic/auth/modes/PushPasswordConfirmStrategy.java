package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.modes.readers.PushPasswordConfirmResponseReader;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.logging.confirm.ConfirmType;
import com.rssl.phizic.security.ConfirmableObject;

/**
 * @author basharin
 * @ created 16.10.13
 * @ $Author$
 * @ $Revision$
 */

public class PushPasswordConfirmStrategy extends OneTimePasswordConfirmStrategy
{
	public static final String PASSWORD_KEY = PushPasswordConfirmStrategy.class.getName() + ".push.password";
	public static final String NAME_BUTTON_NEW = "\"Новый Push-пароль\"";

	public PushPasswordConfirmStrategy()
	{
	}

	protected Class<? extends OneTimePasswordConfirmRequest> getPasswordRequestClass()
	{
		return PushPasswordConfirmRequest.class;
	}

	public ConfirmStrategyType getType()
	{
		return ConfirmStrategyType.push;
	}

	protected ConfirmType getConfirmType()
	{
		return ConfirmType.PUSH;
	}

	public ConfirmRequest createRequest(CommonLogin login, ConfirmableObject value, String sessionId, PreConfirmObject preConfirm) throws SecurityException
	{
		return new PushPasswordConfirmRequest(value);
	}

	public ConfirmResponseReader getConfirmResponseReader()
	{
		return new PushPasswordConfirmResponseReader();
	}

	protected String getPasswordKey()
	{
		return PASSWORD_KEY;
	}

	protected String getNameButton()
	{
		return NAME_BUTTON_NEW;
	}
}
