package com.rssl.phizic.web.ext.sbrf.payments;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author basharin
 * @ created 17.12.14
 * @ $Author$
 * @ $Revision$
 */

public class ViewUDBOTermForm extends ActionFormBase
{
	private String term;

	public void setTerm(String term)
	{
		this.term = term;
	}

	public String getTerm()
	{
		return term;
	}
}
