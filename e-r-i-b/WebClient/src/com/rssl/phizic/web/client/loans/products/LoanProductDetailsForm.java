package com.rssl.phizic.web.client.loans.products;

import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author gladishev
 * @ created 29.01.2008
 * @ $Author$
 * @ $Revision$
 */

public class LoanProductDetailsForm  extends EditFormBase
{
	private String currency;
    private String name;
	private StringBuffer html;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public StringBuffer getHtml()
	{
		return html;
	}

	public void setHtml(StringBuffer html)
	{
		this.html = html;
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}
}

