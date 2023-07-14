package com.rssl.phizic.web.persons;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author lepihina
 * @ created 16.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class ViewOTPRestrictionForm extends ListFormBase
{
	private ActivePerson activePerson;
	private Boolean modified = false;

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public Boolean getModified()
	{
		return modified;
	}

	public void setModified(Boolean modified)
	{
		this.modified = modified;
	}
}
