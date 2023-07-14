package com.rssl.phizic.web.blockingrules;

import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author vagin
 * @ created 21.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListBlockingRulesForm extends ListFormBase
{
	private String selectedMessage;

	public String getSelectedMessage()
	{
		return selectedMessage;
	}

	public void setSelectedMessage(String selectedMessage)
	{
		this.selectedMessage = selectedMessage;
	}
}
