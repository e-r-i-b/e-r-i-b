package com.rssl.phizic.test.web.social.payments;

import com.rssl.phizic.test.web.social.TestSocialForm;

/**
 * @author Jatsky
 * @ created 13.10.14
 * @ $Author$
 * @ $Revision$
 */

public class TestSocialDocumentForm extends TestSocialForm
{
	private String operation;
	private Object document;
	private String id;
	private boolean copying;
	private String template;
	private String confirmSmsPassword;
	private String confirmCardPassword;
	///////////////////////////////////////////////////////////////////////////

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

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public boolean isCopying()
	{
		return copying;
	}

	public void setCopying(boolean copying)
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

	public String getConfirmSmsPassword()
	{
		return confirmSmsPassword;
	}

	public void setConfirmSmsPassword(String confirmSmsPassword)
	{
		this.confirmSmsPassword = confirmSmsPassword;
	}

	public String getConfirmCardPassword()
	{
		return confirmCardPassword;
	}

	public void setConfirmCardPassword(String confirmCardPassword)
	{
		this.confirmCardPassword = confirmCardPassword;
	}
}

