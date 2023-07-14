package com.rssl.phizic.web.ext.sbrf.history;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.type.PersonOldIdentity;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * User: moshenko
 * Date: 14.03.2013
 * Time: 12:51:47
 * Истроия изменения идентификационных данных клиента
 */
public class PersonHistoryIdentityListForm extends ListFormBase<PersonOldIdentity>
{
	private ActivePerson activePerson;
    private Long person;
	private boolean modified = false;

	public boolean isModified()
	{
		return modified;
	}

	public void setModified(boolean modified)
	{
		this.modified = modified;
	}

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public Long getPerson()
	{
		return person;
	}

	public void setPerson(Long person)
	{
		this.person = person;
	}
}
