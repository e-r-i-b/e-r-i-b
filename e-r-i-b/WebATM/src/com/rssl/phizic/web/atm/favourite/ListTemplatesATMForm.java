package com.rssl.phizic.web.atm.favourite;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.List;

/**
 * @author Dorzhinov
 * @ created 11.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListTemplatesATMForm extends ListFormBase
{
	private List<TemplateDocument> templates; //шаблоны платежей
	private String supportedForms; //названия поддерживаемых форм через запятую

	private String status;
	private String[] form;
	private String[] type;

	public List<TemplateDocument> getTemplates()
	{
		return templates;
	}

	public void setTemplates(List<TemplateDocument> templates)
	{
		this.templates = templates;
	}

	public String getSupportedForms()
	{
		return supportedForms;
	}

	public void setSupportedForms(String supportedForms)
	{
		this.supportedForms = supportedForms;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String[] getForm()
	{
		return form;
	}

	public void setForm(String[] form)
	{
		this.form = form;
	}

	public String[] getType()
	{
		return type;
	}

	public void setType(String[] type)
	{
		this.type = type;
	}
}
