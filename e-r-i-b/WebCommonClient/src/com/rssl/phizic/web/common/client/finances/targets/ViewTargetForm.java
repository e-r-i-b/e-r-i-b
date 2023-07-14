package com.rssl.phizic.web.common.client.finances.targets;

import com.rssl.phizic.web.actions.payments.forms.ConfirmPaymentByFormForm;

/**
 * @author akrenev
 * @ created 04.06.2013
 * @ $Author$
 * @ $Revision$
 *
 * ����� ������������� ������ �� �������� ������ ��� ����
 */

public class ViewTargetForm extends ConfirmPaymentByFormForm
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
