package com.rssl.phizic.web.common.client.finances.targets;

import com.rssl.phizic.web.actions.payments.forms.ConfirmPaymentByFormForm;

/**
 * @author akrenev
 * @ created 04.06.2013
 * @ $Author$
 * @ $Revision$
 *
 * Форма подтверждения заявки на открытие вклада для цели
 */

public class ViewTargetForm extends ConfirmPaymentByFormForm
{
	private Long targetId;

	/**
	 * @return идентификатор цели
	 */
	public Long getTargetId()
	{
		return targetId;
	}

	/**
	 * задать идентификатор цели
	 * @param targetId идентификатор цели
	 */
	public void setTargetId(Long targetId)
	{
		this.targetId = targetId;
	}
}
