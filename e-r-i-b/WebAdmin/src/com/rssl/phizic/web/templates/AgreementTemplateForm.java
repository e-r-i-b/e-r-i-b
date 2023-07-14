package com.rssl.phizic.web.templates;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.business.template.DocTemplate;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: Novikov_A Date: 26.01.2007 Time: 13:02:31 To change this template use File
 * | Settings | File Templates.
 */
public class AgreementTemplateForm extends ActionFormBase
{
	private Long              person;
	private ActivePerson      client;
	private List<DocTemplate> templates;
	private PersonDocument    activeDocument;

	public void setPerson(Long person)
	{
		this.person = person;
	}

	public Long getPerson()
	{
		return this.person;
	}

	public void setClient(ActivePerson client)
	{
		this.client = client;
	}

	public ActivePerson getClient()
	{
		return this.client;
	}

	public void setTemplates(List templates)
	{
		this.templates = templates;
	}

	public List getTemplates()
	{
		return this.templates;
	}

	public PersonDocument getActiveDocument()
	{
		return activeDocument;
	}

	public void setActiveDocument(PersonDocument activeDocument)
	{
		this.activeDocument = activeDocument;
	}
}
