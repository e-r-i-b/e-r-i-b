package com.rssl.phizic.web.common.mobile.favourite;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
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
	private List<TemplateDocument> templates; //������� ��������
	private String supportedForms; //�������� �������������� ���� ����� �������

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
}
