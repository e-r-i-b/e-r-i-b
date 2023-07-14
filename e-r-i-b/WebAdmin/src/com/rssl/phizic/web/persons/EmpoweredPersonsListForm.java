package com.rssl.phizic.web.persons;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 24.07.2006
 * @ $Author: hudyakov $
 * @ $Revision: 10511 $
 */
@SuppressWarnings({"ReturnOfCollectionOrArrayField", "AssignmentToCollectionOrArrayFieldFromParameter"})
public class EmpoweredPersonsListForm extends ListFormBase
{
	private Long person;
	private ActivePerson activePerson;
	private List<ActivePerson> empoweredPersons;
	private Boolean    modified=false;

	public Boolean getModified()
	{
		return modified;
	}

	public void setModified(Boolean modified)
	{
		this.modified = modified;
	}
	public Long getPerson()
	{
		return person;
	}

	public void setPerson(Long person)
	{
		this.person = person;
	}

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public List<ActivePerson> getEmpoweredPersons()
	{
		return empoweredPersons;
	}

	public void setEmpoweredPersons(List<ActivePerson> empoweredPersons)
	{
		this.empoweredPersons = empoweredPersons;
	}

}