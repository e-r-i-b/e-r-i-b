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
	 * @return ������������� ����
	 */
	public Long getTargetId()
	{
		return targetId;
	}

	/**
	 * ������ ������������� ����
	 * @param targetId ������������� ����
	 */
	public void setTargetId(Long targetId)
	{
		this.targetId = targetId;
	}
}
