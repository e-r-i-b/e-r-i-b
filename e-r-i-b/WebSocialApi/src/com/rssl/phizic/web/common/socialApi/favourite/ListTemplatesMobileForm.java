package com.rssl.phizic.web.common.socialApi.favourite;

import com.rssl.phizic.web.common.ListFormBase;

import java.util.List;

/**
 * @author Dorzhinov
 * @ created 11.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListTemplatesMobileForm extends ListFormBase
{
	private List<Object[]> templates; //шаблоны платежей
	private String supportedForms; //названия поддерживаемых форм через запятую

	public List<Object[]> getTemplates()
	{
		return templates;
	}

	public void setTemplates(List<Object[]> templates)
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
}
