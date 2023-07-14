package com.rssl.phizic.web.tabs;

import org.apache.struts.action.ActionForm;
import com.rssl.auth.csa.front.business.tabs.Tabs;

import java.util.List;

/**
 * @author osminin
 * @ created 15.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class AsyncListTabsForm extends ActionForm
{
	private List<Tabs> tabs; // список вкладок с данными

	public List<Tabs> getTabs()
	{
		return tabs;
	}

	public void setTabs(List<Tabs> tabs)
	{
		this.tabs = tabs;
	}
}
