package com.rssl.phizic.web.gate.services.types;

import com.rssl.phizic.gate.clients.User;
import com.rssl.phizic.gate.clients.UserCategory;

/**
 * @author egorova
 * @ created 19.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class UserImpl extends ClientImpl implements User
{
	private UserCategory category;

	public UserCategory getCategory()
	{
		return category;
	}

	public void setCategory(UserCategory category)
	{
		this.category = category;
	}

	public void setCategory(String category)
	{
		this.category = UserCategory.valueOf(category);
	}
}
