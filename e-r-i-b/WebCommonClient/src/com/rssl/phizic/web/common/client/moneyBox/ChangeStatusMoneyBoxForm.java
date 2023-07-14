package com.rssl.phizic.web.common.client.moneyBox;

import com.rssl.phizic.web.actions.payments.forms.CreatePaymentForm;

/**
 * @author tisov
 * @ created 03.10.14
 * @ $Author$
 * @ $Revision$
 */

public class ChangeStatusMoneyBoxForm extends CreatePaymentForm
{
	private String changePaymentStatusType;

	public String getChangePaymentStatusType()
	{
		return changePaymentStatusType;
	}

	public void setChangePaymentStatusType(String changePaymentStatusType)
	{
		this.changePaymentStatusType = changePaymentStatusType;
	}
}
