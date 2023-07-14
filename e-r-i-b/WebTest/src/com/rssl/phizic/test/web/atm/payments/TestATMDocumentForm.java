package com.rssl.phizic.test.web.atm.payments;

import com.rssl.phizic.test.web.atm.TestATMForm;

/**
 * @author Erkin
 * @ created 10.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class TestATMDocumentForm extends TestATMForm
{
	private Long   id;

	private String documentStatus;

	private String operation;

	private Object document;

	private String template;

	private Boolean copying;
	///////////////////////////////////////////////////////////////////////////

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getDocumentStatus()
	{
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus)
	{
		this.documentStatus = documentStatus;
	}

	public String getOperation()
	{
		return operation;
	}

	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	public Object getDocument()
	{
		return document;
	}

	public void setDocument(Object document)
	{
		this.document = document;
	}

	public Boolean getCopying()
	{
		return copying;
	}

	public void setCopying(Boolean copying)
	{
		this.copying = copying;
	}

	public String getTemplate()
	{
		return template;
	}

	public void setTemplate(String template)
	{
		this.template = template;
	}
}