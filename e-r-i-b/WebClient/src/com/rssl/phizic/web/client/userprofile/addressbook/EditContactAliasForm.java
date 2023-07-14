package com.rssl.phizic.web.client.userprofile.addressbook;

import com.rssl.phizic.web.common.EditFormBase;

/**
 * Форма редактирования алиаса контакта
 *
 * @author shapin
 * @ created 07.10.14
 * @ $Author$
 * @ $Revision$
 */

public class EditContactAliasForm extends EditFormBase
{
	private String alias;
	private String error;

	public String getAlias()
	{
		return alias;
	}

	public void setAlias(String alias)
	{
		this.alias = alias;
	}

	public String getError()
	{
		return error;
	}

	public void setError(String error)
	{
		this.error = error;
	}
}
