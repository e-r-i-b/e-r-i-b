package com.rssl.phizic.business.loanclaim.ukoClaim;

import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityLogicException;

/**
 * @author Gulov
 * @ created 05.03.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Подтверждение заявки, или обратного звонка гостевой заявки.
 */
public class Confirmable implements ConfirmableObject
{
	//ИД заявки на кредит
	private Long id;

	public Confirmable(Long id)
	{
		this.id = id;
	}

	public Long getId()
	{
		return id;
	}

	public byte[] getSignableObject() throws SecurityException, SecurityLogicException
	{
		return null;
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)	{ }
}
