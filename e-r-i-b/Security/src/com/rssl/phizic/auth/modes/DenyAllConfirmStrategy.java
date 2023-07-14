package com.rssl.phizic.auth.modes;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.util.List;
import java.util.Collections;

/**
 * Стратегия, не позволяющая подтверждать
 * @author Pankin
 * @ created 03.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class DenyAllConfirmStrategy extends NotConfirmStrategy
{
	private static final String MESSAGE_DENY = "Операция запрещена.";

	public ConfirmStrategyType getType()
	{
		return ConfirmStrategyType.DENY;
	}

	public ConfirmRequest createRequest(CommonLogin login, ConfirmableObject value, String sessionId, PreConfirmObject preConfirm) throws SecurityException
	{
		return new ErrorConfirmRequest(ConfirmStrategyType.DENY, MESSAGE_DENY);
	}

	public ConfirmResponseReader getConfirmResponseReader()
	{
		// Ридер, всегда возвращающий ошибку
		return new ConfirmResponseReader()
		{
			public void setValuesSource(FieldValuesSource valuesSource)
			{
			}

			public boolean read()
			{
				return false;
			}

			public ConfirmResponse getResponse()
			{
				return null;
			}

			public List<String> getErrors()
			{
				return Collections.singletonList(MESSAGE_DENY);
			}
		};
	}

	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}
