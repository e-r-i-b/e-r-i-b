package com.rssl.phizic.web.common.mobile.payments;

import com.rssl.phizic.web.actions.ActionFormBase;

import java.math.BigDecimal;

/**
 * @ author: Gololobov
 * @ created: 06.10.14
 * @ $Author$
 * @ $Revision$
 * Форма получения суммы по умолчанию для оплаты мобильной связи
 */
public class ShowMobilePhoneDefaultSumPayForm extends ActionFormBase
{
	private BigDecimal phonePayDefaultSum;

	public BigDecimal getPhonePayDefaultSum()
	{
		return phonePayDefaultSum;
	}

	public void setPhonePayDefaultSum(BigDecimal phonePayDefaultSum)
	{
		this.phonePayDefaultSum = phonePayDefaultSum;
	}
}
