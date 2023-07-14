package com.rssl.phizic.test.limitsApp;

import org.apache.struts.action.ActionForm;

/**
 * @author osminin
 * @ created 27.01.14
 * @ $Author$
 * @ $Revision$
 */
public class RollbackTransactionForm extends ActionForm
{
	private String error;

	private String externalId;
	private String documentExternalId;
	private String operationDate;

	public String getError()
	{
		return error;
	}

	public void setError(String error)
	{
		this.error = error;
	}

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public String getDocumentExternalId()
	{
		return documentExternalId;
	}

	public void setDocumentExternalId(String documentExternalId)
	{
		this.documentExternalId = documentExternalId;
	}

	public String getOperationDate()
	{
		return operationDate;
	}

	public void setOperationDate(String operationDate)
	{
		this.operationDate = operationDate;
	}
}
