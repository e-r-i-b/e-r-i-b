package com.rssl.phizic.web.persons;

import org.apache.struts.action.ActionForm;

/**
 * Форма для редактирования аватара клинта
 * @author miklyaev
 * @ created 02.06.14
 * @ $Author$
 * @ $Revision$
 */

public class AsyncEditAvatarForm extends ActionForm
{
	private long loginId;

	public long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(long loginId)
	{
		this.loginId = loginId;
	}
}
