package com.rssl.phizic.web.loans.statemessages;

import java.util.Properties;

import com.rssl.phizic.web.common.ListFormBase;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 21.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class ListLoanStateMessagesForm extends ListFormBase
{
	private Properties properties;
	private boolean  selectAll   = false;

	public Properties getProperties()
	{
		return properties;
	}

	public void setProperties(Properties pr)
	{
		this.properties = pr;
	}

	public boolean isSelectAll()
	{
		return selectAll;
	}

	public void setSelectAll(boolean selectAll)
	{
		this.selectAll = selectAll;
	}
}
