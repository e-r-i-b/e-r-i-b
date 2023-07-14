package com.rssl.phizic.web.ext.sbrf.persons.templates;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.actions.payments.forms.ViewTemplateForm;

/**
 * @ author: Vagin
 * @ created: 16.04.2013
 * @ $Author
 * @ $Revision
 * Форма просмотра формы шаблона клиента
 */
public class EmployeeViewTemplateForm extends ViewTemplateForm
{
	private Long person;
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

	public Long getPerson()
	{
		return person;
	}

	public void setPerson(Long person)
	{
		this.person = person;
	}
}
