package com.rssl.phizic.web.common.mobile.ext.sbrf.userprofile;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * ‘орма скрыти€ продуктов в мобильном приложении
 * @author Pankin
 * @ created 26.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class HideProductsForm extends ActionFormBase
{
	private String[] resource = new String[]{};

	public String[] getResource()
	{
		return resource;
	}

	public void setResource(String[] resource)
	{
		this.resource = resource;
	}
}
