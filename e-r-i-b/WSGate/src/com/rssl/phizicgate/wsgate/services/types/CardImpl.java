package com.rssl.phizicgate.wsgate.services.types;

import com.rssl.phizgate.common.routable.CardBase;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author: Pakhomova
 * @created: 20.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class CardImpl extends CardBase
{
	private long     ownerId;
	private long     paymentSystemId;

	public long getOwnerId()
	{
		return ownerId;
	}

	public void setOwnerId(long ownerId)
	{
		this.ownerId = ownerId;
	}

	public long getPaymentSystemId()
	{
		return paymentSystemId;
	}

	public  void setPaymentSystemId(long paymentSystemId)
	{
		this.paymentSystemId = paymentSystemId;
	}

	public boolean equals(Object o)
    {
	    if ( this == o )
	    {
		    return true;
	    }
	    if ( o == null || getClass() != o.getClass() )
	    {
		    return false;
	    }

        final CardImpl card = (CardImpl) o;

	    if ( !id.equals(card.getId()) )
	    {
		    return false;
	    }

        return true;
    }

    public int hashCode()
    {
        return 0; //todo
    }

	public void setCardType(String cardType)
	{
		if (cardType == null || cardType.trim().length() == 0)
		{
			return;
		}
		this.cardType = Enum.valueOf(CardType.class, cardType);
	}

	public void setCardState(String cardState)
	{
		if (cardState == null || cardState.trim().length() == 0)
		{
			return;
		}
		this.cardState = Enum.valueOf(CardState.class, cardState);
	}

	public void setAdditionalCardType(String additionalCardType)
	{
		if (additionalCardType == null || additionalCardType.trim().length() == 0)
		{
			return;
		}
		this.additionalCardType = Enum.valueOf(AdditionalCardType.class, additionalCardType);
	}

	/**
	 * задать тип данных отчета
	 * @param reportDeliveryType тип данных отчета
	 */
	public void setReportDeliveryType(String reportDeliveryType)
	{
		if (StringHelper.isNotEmpty(reportDeliveryType))
			super.setReportDeliveryType(ReportDeliveryType.valueOf(reportDeliveryType));
	}

	/**
	 * задать язык отчета
	 * @param reportDeliveryLanguage язык отчета
	 */
	public void setReportDeliveryLanguage(String reportDeliveryLanguage)
	{
		if (StringHelper.isNotEmpty(reportDeliveryLanguage))
			super.setReportDeliveryLanguage(ReportDeliveryLanguage.valueOf(reportDeliveryLanguage));
	}
}