package com.rssl.phizic.web.common.socialApi.ext.sbrf.userprofile;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * ‘орма скрыти€ шаблонов из мобильного приложени€
 * @author Pankin
 * @ created 08.10.2012
 * @ $Author$
 * @ $Revision$
 */

public class HideTemplatesForm extends ActionFormBase
{
	private String[] template = new String[]{};

	public String[] getTemplate()
	{
		return template;
	}

	public void setTemplate(String[] template)
	{
		this.template = template;
	}
}
