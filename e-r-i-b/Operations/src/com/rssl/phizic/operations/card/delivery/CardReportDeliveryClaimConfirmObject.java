package com.rssl.phizic.operations.card.delivery;

import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityLogicException;

/**
 * @author akrenev
 * @ created 13.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * Объект для подтверждения заявки на подтверждение операции
 */

public class CardReportDeliveryClaimConfirmObject implements ConfirmableObject
{
	private final Long id;

	/**
	 * конструктор
	 * @param id идентификатор
	 */
	public CardReportDeliveryClaimConfirmObject(Long id)
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

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType){}
}
