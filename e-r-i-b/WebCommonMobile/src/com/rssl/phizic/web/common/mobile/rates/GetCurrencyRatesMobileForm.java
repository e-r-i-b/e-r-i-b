package com.rssl.phizic.web.common.mobile.rates;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author Dorzhinov
 * @ created 27.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class GetCurrencyRatesMobileForm extends ActionFormBase
{
	private String type;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}
}
