package com.rssl.phizic.business.payments;

/**
 * @author Erkin
 * @ created 18.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class PersonalPaymentCard
{
	private Long id;

	private String cardNumber;

	private Long billingId;

	private String billingName;

	///////////////////////////////////////////////////////////////////////////

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public Long getBillingId()
	{
		return billingId;
	}

	public void setBillingId(Long billingId)
	{
		this.billingId = billingId;
	}

	public String getBillingName()
	{
		return billingName;
	}

	public void setBillingName(String billingName)
	{
		this.billingName = billingName;
	}
}
