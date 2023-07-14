package com.rssl.phizic.web.actions.templates;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 11.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoadBanksDocumentForm extends ActionFormBase
{
	private Long     id;
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long tmpIid)
	{
		this.id = tmpIid;
	}
}
