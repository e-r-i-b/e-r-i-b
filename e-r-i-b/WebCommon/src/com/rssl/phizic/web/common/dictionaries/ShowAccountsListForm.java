package com.rssl.phizic.web.common.dictionaries;

import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author Omeliyanchuk
 * @ created 07.02.2007
 * @ $Author$
 * @ $Revision$
 */

public class ShowAccountsListForm extends ListFormBase
{
	private long personId;
	public long getPersonId()
	{
		return personId;
	}

	public void setPersonId(long personId)
	{
		this.personId = personId;
	}
}
