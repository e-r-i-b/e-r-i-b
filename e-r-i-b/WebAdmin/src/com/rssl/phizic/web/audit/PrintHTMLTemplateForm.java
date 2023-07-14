package com.rssl.phizic.web.audit;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 11.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class PrintHTMLTemplateForm extends ActionFormBase
{
	private Long templateId;
	private Long documentId;
	private String html;


	public Long getTemplateId()
	{
		return templateId;
	}

	public void setTemplateId(Long templateId)
	{
		this.templateId = templateId;
	}

	public Long getDocumentId()
	{
		return documentId;
	}

	public void setDocumentId(Long documentId)
	{
		this.documentId = documentId;
	}

	public String getHtml()
	{
		return html;
	}

	public void setHtml(String html)
	{
		this.html = html;
	}
}
