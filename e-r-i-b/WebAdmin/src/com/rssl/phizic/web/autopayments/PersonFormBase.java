package com.rssl.phizic.web.autopayments;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.common.ListLimitActionForm;

/**
 * @author khudyakov
 * @ created 12.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class PersonFormBase<T> extends ListLimitActionForm<T>
{
	private ActivePerson activePerson;
	private boolean modified;

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public boolean isModified()
	{
		return modified;
	}

	public void setModified(boolean modified)
	{
		this.modified = modified;
	}
}
