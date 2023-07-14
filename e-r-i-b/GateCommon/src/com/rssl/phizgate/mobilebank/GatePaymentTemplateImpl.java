package com.rssl.phizgate.mobilebank;

import com.rssl.phizic.gate.mobilebank.GatePaymentTemplate;

/**
 * @author Erkin
 * @ created 06.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class GatePaymentTemplateImpl implements GatePaymentTemplate
{
	private String externalId;


	private String cardNumber;

	private String phone;

	private String recipientCode;

	private String payerCode;

	private boolean active;

	///////////////////////////////////////////////////////////////////////////

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getRecipientCode()
	{
		return recipientCode;
	}

	public void setRecipientCode(String recipientCode)
	{
		this.recipientCode = recipientCode;
	}

	public String getPayerCode()
	{
		return payerCode;
	}

	public void setPayerCode(String payerCode)
	{
		this.payerCode = payerCode;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public int hashCode()
	{
		return externalId.hashCode();
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		GatePaymentTemplateImpl that = (GatePaymentTemplateImpl) o;

		return this.getExternalId().equals(that.getExternalId());
	}

	public String toString()
	{
		return "GatePaymentTemplateImpl{" +
				"externalId='" + externalId + '\'' +
				'}';
	}
}
