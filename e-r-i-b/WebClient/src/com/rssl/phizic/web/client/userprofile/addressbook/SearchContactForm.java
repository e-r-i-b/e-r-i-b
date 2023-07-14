package com.rssl.phizic.web.client.userprofile.addressbook;

import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * Форма поиска контактов по номеру телефона.
 *
 * @author bogdanov
 * @ created 20.10.14
 * @ $Author$
 * @ $Revision$
 */

public class SearchContactForm extends ActionFormBase
{
	private String ids;
	private boolean error;
	private String phone;
	private List<String> foundIds;

	public boolean isError()
	{
		return error;
	}

	public void setError(boolean error)
	{
		this.error = error;
	}

	public List<String> getFoundIds()
	{
		return foundIds;
	}

	public void setFoundIds(List<String> foundIds)
	{
		this.foundIds = foundIds;
	}

	public String getIds()
	{
		return ids;
	}

	public void setIds(String ids)
	{
		this.ids = ids;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}
}
