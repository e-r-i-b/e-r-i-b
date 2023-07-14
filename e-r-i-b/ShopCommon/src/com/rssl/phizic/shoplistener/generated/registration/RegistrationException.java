package com.rssl.phizic.shoplistener.generated.registration;

import com.rssl.phizic.gate.exceptions.GateException;

/**
 * Исключение при регистрации заказа
 * @author gladishev
 * @ created 27.02.14
 * @ $Author$
 * @ $Revision$
 */
public class RegistrationException extends GateException
{
	private Long stateCode;
	private String stateDescription;

	public RegistrationException(Long stateCode, String stateDescription)
	{
		this.stateCode = stateCode;
		this.stateDescription = stateDescription;
	}

	public Long getStateCode()
	{
		return stateCode;
	}

	public String getStateDescription()
	{
		return stateDescription;
	}
}
