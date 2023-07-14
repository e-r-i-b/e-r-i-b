package com.rssl.phizic.common.type;

import com.rssl.phizic.common.types.UUID;

import java.util.Date;

/**
 * Сообщение СОС о результате списания абонентской платы
 * @author Puzikov
 * @ created 03.06.14
 * @ $Author$
 * @ $Revision$
 */

public class ServiceFeeResultRequest
{
	private String rqVersion;

	private UUID rqUID;

	private Date rqTime;

	private ErmbProfileIdentity clientIdentity;

	private ErmbResourceType resourceType;

	private String account;

	private String card;

	private String loan;

	private String tb;

	private String paymentStatus;

	private String paymentID;

	public String getRqVersion()
	{
		return rqVersion;
	}

	public void setRqVersion(String rqVersion)
	{
		this.rqVersion = rqVersion;
	}

	public UUID getRqUID()
	{
		return rqUID;
	}

	public void setRqUID(UUID rqUID)
	{
		this.rqUID = rqUID;
	}

	public Date getRqTime()
	{
		return rqTime;
	}

	public void setRqTime(Date rqTime)
	{
		this.rqTime = rqTime;
	}

	public ErmbProfileIdentity getClientIdentity()
	{
		return clientIdentity;
	}

	public void setClientIdentity(ErmbProfileIdentity clientIdentity)
	{
		this.clientIdentity = clientIdentity;
	}

	public ErmbResourceType getResourceType()
	{
		return resourceType;
	}

	public void setResourceType(ErmbResourceType resourceType)
	{
		this.resourceType = resourceType;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public String getCard()
	{
		return card;
	}

	public void setCard(String card)
	{
		this.card = card;
	}

	public String getLoan()
	{
		return loan;
	}

	public void setLoan(String loan)
	{
		this.loan = loan;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

	public String getPaymentStatus()
	{
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus)
	{
		this.paymentStatus = paymentStatus;
	}

	public String getPaymentID()
	{
		return paymentID;
	}

	public void setPaymentID(String paymentID)
	{
		this.paymentID = paymentID;
	}
}
