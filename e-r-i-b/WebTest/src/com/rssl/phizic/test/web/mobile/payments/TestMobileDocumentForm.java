package com.rssl.phizic.test.web.mobile.payments;

import com.rssl.phizic.test.web.mobile.TestMobileForm;

/**
 * @author Erkin
 * @ created 10.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class TestMobileDocumentForm extends TestMobileForm
{
	private String operation;
	private Object document;
	private String id;
	private boolean copying;
	private String template;
	private String confirmSmsPassword;
	private String confirmCardPassword;
	private boolean editSupported = true;
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

	public boolean isEditSupported()
	{
		return editSupported;
	}

	public void setEditSupported(boolean editSupported)
	{
		this.editSupported = editSupported;
	}
}
