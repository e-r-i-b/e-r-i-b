package com.rssl.phizic.web.common.client.ima;

import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author Mescheryakova
 * @ created 16.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListOfficeIMAForm extends ListFormBase
{
	private String parentSynchKey;

	public String getParentSynchKey()
	{
		return parentSynchKey;
	}

	public void setParentSynchKey(String parentSynchKey)
	{
		this.parentSynchKey = parentSynchKey;
	}
}
