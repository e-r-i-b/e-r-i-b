package com.rssl.phizic.test.limitsApp;

import org.apache.struts.action.ActionForm;

/**
 * @author osminin
 * @ created 23.01.14
 * @ $Author$
 * @ $Revision$
 */
public class AddTransactionForm extends ActionForm
{
	private String firstName;
	private String surName;
	private String patrName;
	private String docSeries;
	private String docNumber;
	private String tb;
	private String birthDate;

	private String externalId;
	private String documentExternalId;
	private String amountValue;
	private String currency;
	private String operationDate;
	private String channelType;
	private String limits;

	private String error;

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getPatrName()
	{
		return patrName;
	}

	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public String getDocSeries()
	{
		return docSeries;
	}

	public void setDocSeries(String docSeries)
	{
		this.docSeries = docSeries;
	}

	public String getDocNumber()
	{
		return docNumber;
	}

	public void setDocNumber(String docNumber)
	{
		this.docNumber = docNumber;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

	public String getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(String birthDate)
	{
		this.birthDate = birthDate;
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

	public String getAmountValue()
	{
		return amountValue;
	}

	public void setAmountValue(String amountValue)
	{
		this.amountValue = amountValue;
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public String getOperationDate()
	{
		return operationDate;
	}

	public void setOperationDate(String operationDate)
	{
		this.operationDate = operationDate;
	}

	public String getChannelType()
	{
		return channelType;
	}

	public void setChannelType(String channelType)
	{
		this.channelType = channelType;
	}

	public String getLimits()
	{
		return limits;
	}

	public void setLimits(String limits)
	{
		this.limits = limits;
	}

	public String getError()
	{
		return error;
	}

	public void setError(String error)
	{
		this.error = error;
	}
}
