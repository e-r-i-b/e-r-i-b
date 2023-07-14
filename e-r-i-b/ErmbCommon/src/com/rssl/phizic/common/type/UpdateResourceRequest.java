package com.rssl.phizic.common.type;

import com.rssl.phizic.common.types.UUID;

import java.util.Calendar;

/**
 * Сообщение об изменении продуктов клиента в ЕРМБ
 * @author Rtischeva
 * @ created 01.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class UpdateResourceRequest
{
	private String rqVersion;

	private UUID rqUID;

	private Calendar rqTime;

    private ErmbProfileIdentity clientIdentity;

	private ErmbResourceType resourceType;

	private String account;

	private String card;

	private String loan;

	private String tb;

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

	public Calendar getRqTime()
	{
		return rqTime;
	}

	public void setRqTime(Calendar rqTime)
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

	public ErmbResourceType getResourceType (){
        return resourceType;
    }

    public void setResourceType (ErmbResourceType resourceType){
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
}
