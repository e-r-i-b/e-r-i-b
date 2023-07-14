package com.rssl.phizic.web.common.socialApi.finances.targets;

import com.rssl.phizic.web.common.socialApi.payments.ConfirmSocialPaymentByFormForm;

/**
 * @author  Balovtsev
 * @version 03.10.13 9:53
 */
public class ViewTargetSocialForm extends ConfirmSocialPaymentByFormForm
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
