package com.rssl.phizic.web.templates;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * Created by IntelliJ IDEA.
 * User: Novikov_A
 * Date: 16.02.2007
 * Time: 16:07:05
 * To change this template use File | Settings | File Templates.
 */
public class ShowDictionaryPackageForm extends ListFormBase
{
	private Long         personId;
	private ActivePerson activePerson;

	public Long getPerson ()
	{
	    return personId;
	}

	public void setPerson ( Long person )
	{
	    this.personId=person;
	}

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}
}