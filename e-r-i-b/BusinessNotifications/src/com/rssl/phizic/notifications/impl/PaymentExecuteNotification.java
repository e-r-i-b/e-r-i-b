package com.rssl.phizic.notifications.impl;

import com.rssl.phizic.notifications.Notification;

/**
 * @author eMakarov
 * @ created 29.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class PaymentExecuteNotification extends AccountNotificationBase
{
	private Double transactionSum;
	private String currencyCode;
	private String nameOrType = "";
	private String documentState;
	private String documentType;
	private String nameAutoPayment;

	public Class<? extends Notification> getType()
	{
		return PaymentExecuteNotification.class;
	}

	public Double getTransactionSum()
	{
		return transactionSum;
	}

	public void setTransactionSum(Double transactionSum)
	{
		this.transactionSum = transactionSum;
	}

	public String getCurrencyCode()
	{
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode)
	{
		this.currencyCode = currencyCode;
	}
	public String getNameOrType()
	{
		return nameOrType;
	}

	public void setNameOrType(String nameOrType)
	{
		this.nameOrType = nameOrType;
	}

	public String getDocumentState()
	{
		return documentState;
	}

	public void setDocumentState(String documentState)
	{
		this.documentState = documentState;
	}

	public String getDocumentType()
	{
		return documentType;
	}

	public void setDocumentType(String documentType)
	{
		this.documentType = documentType;
	}

	public String getNameAutoPayment()
	{
		return nameAutoPayment;
	}

	public void setNameAutoPayment(String nameAutoPayment)
	{
		this.nameAutoPayment = nameAutoPayment;
	}
}
