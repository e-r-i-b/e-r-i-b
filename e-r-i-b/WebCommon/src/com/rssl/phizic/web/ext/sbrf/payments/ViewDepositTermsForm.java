package com.rssl.phizic.web.ext.sbrf.payments;

import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author Pankin
 * @ created 07.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class ViewDepositTermsForm extends EditFormBase
{
	private String html;
	private String termsType;
	private boolean loadInDiv = false;

	public String getHtml()
	{
		return html;
	}

	public void setHtml(String html)
	{
		this.html = html;
	}

	public String getTermsType()
	{
		return termsType;
	}

	public void setTermsType(String termsType)
	{
		this.termsType = termsType;
	}

	public boolean isLoadInDiv()
	{
		return loadInDiv;
	}

	public void setLoadInDiv(boolean loadInDiv)
	{
		this.loadInDiv = loadInDiv;
	}
}
