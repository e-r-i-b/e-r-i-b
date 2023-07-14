package com.rssl.phizic.business.extendedattributes;

/**
 * @author Rtischeva
 * @ created 20.03.15
 * @ $Author$
 * @ $Revision$
 */
public class ClientHistoryExtendedAttribute extends ExtendedAttribute
{
	private Long paymentId;

	public ClientHistoryExtendedAttribute()
	{
	}

	public ClientHistoryExtendedAttribute(Long id, Long paymentId, String name, String type, Object internalValue)
	{
		super(id, name, type, internalValue);
		this.paymentId = paymentId;
	}

	public Long getPaymentId()
	{
		return paymentId;
	}

	public void setPaymentId(Long paymentId)
	{
		this.paymentId = paymentId;
	}
}
