package com.rssl.phizic.web.common.socialApi.deposits;

import com.rssl.phizic.web.common.client.deposits.DepositDetailsForm;

/**
 * User: Moshenko
 * Date: 22.02.12
 * Time: 16:05
 */
public class DepositDetailsMobileForm  extends DepositDetailsForm
{

	/**
	 * дополнительные методы нужны для реализации полного соответствия
	 * спецификации Mobile API 3.0
	 * @return
	 */

	public Long getDepositId()
	{
		return id;
	}

	public void setDepositId(Long depositId)
	{
		this.id = depositId;
	}
}
