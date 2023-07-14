package com.rssl.phizic.web.client.favourite;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lukina
 * @ created 18.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class ListPaymentsAndTemplatesForm extends ListFormBase
{
	private List<TemplateDocument> activeTemplates = new ArrayList<TemplateDocument>();
	private List<TemplateDocument> inactiveTemplates = new ArrayList<TemplateDocument>();


	public List<TemplateDocument> getActiveTemplates()
	{
		return activeTemplates;
	}

	public void setActiveTemplates(List<TemplateDocument> activeTemplates)
	{
		this.activeTemplates = activeTemplates;
	}

	public void addActiveTemplate(TemplateDocument data)
	{
		activeTemplates.add(data);
	}

	public List<TemplateDocument> getInactiveTemplates()
	{
		return inactiveTemplates;
	}

	public void setInactiveTemplates(List<TemplateDocument> inactiveTemplates)
	{
		this.inactiveTemplates = inactiveTemplates;
	}

	public void addInactiveTemplate(TemplateDocument data)
	{
		inactiveTemplates.add(data);
	}
}

