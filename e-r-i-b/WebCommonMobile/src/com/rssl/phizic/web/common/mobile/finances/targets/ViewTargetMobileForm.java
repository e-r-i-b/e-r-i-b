package com.rssl.phizic.web.common.mobile.finances.targets;

import com.rssl.phizic.web.common.mobile.payments.ConfirmMobilePaymentByFormForm;

/**
 * @author  Balovtsev
 * @version 03.10.13 9:53
 */
public class ViewTargetMobileForm extends ConfirmMobilePaymentByFormForm
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
