package com.rssl.phizic.web.client.ext.sbrf.mobilebank;

import com.rssl.phizic.web.actions.payments.IndexForm;

/**
 * @author lukina
 * @ created 03.03.2011
 * @ $Author$
 * @ $Revision$
 * @deprecated избавление от МБК
 */
@Deprecated
//todo CHG059738 удалить
public class SelectCategoryForm extends IndexForm
{
	private String phoneCode;

	private String cardCode;

	public String getPhoneCode()
	{
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode)
	{
		this.phoneCode = phoneCode;
	}

	public String getCardCode()
	{
		return cardCode;
	}

	public void setCardCode(String cardCode)
	{
		this.cardCode = cardCode;
	}
}