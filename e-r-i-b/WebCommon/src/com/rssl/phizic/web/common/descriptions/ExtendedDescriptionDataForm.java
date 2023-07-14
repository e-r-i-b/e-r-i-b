package com.rssl.phizic.web.common.descriptions;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author niculichev
 * @ created 08.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedDescriptionDataForm extends ActionFormBase
{
	private String name;
	private String content;

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
