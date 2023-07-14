package com.rssl.phizic.web.persons;

import com.rssl.phizic.business.groups.Group;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * User: Omeliyanchuk
 * Date: 15.11.2006
 * Time: 11:18:41
 */
public class ListPersonGroupsForm extends ListFormBase<Group>
{
	private String mode;
	private Long personId;
	private boolean modified;
	private ActivePerson activePerson;

	public Long getPerson()
    {
        return personId;
    }

    public void setPerson(Long personId)
    {
        this.personId = personId;
    }
	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public String getMode()
	{
		return mode;
	}

	public void setMode(String mode)
	{
		this.mode = mode;
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
