package com.rssl.phizic.web.client.loans;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author gladishev
 * @ created 26.11.2007
 * @ $Author$
 * @ $Revision$
 */

public class LoanCalculatorForm extends ActionFormBase
{
	private Long         id;
	private StringBuffer html;

	public Long getId()
	{
	    return id;
	}

	public void setId(Long id)
	{
	    this.id = id;
	}

	public StringBuffer getHtml()
	{
	    return html;
	}

	public void setHtml(StringBuffer html)
	{
	    this.html = html;
	}

}
