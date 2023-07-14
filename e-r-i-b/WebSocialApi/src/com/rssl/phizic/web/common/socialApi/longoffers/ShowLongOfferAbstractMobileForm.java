package com.rssl.phizic.web.common.socialApi.longoffers;

import com.rssl.phizic.web.common.client.longoffers.ShowLongOfferInfoForm;

/**
 * Form для отображения графика автоплатежей типа LongOffer в мобильной версии
 * @ author Rtischeva
 * @ created 07.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class ShowLongOfferAbstractMobileForm extends ShowLongOfferInfoForm
{
	private String from;
	private String to;

	public String getFrom()
	{
		return from;
	}

	public void setFrom(String from)
	{
		this.from = from;
	}

	public String getTo()
	{
		return to;
	}

	public void setTo(String to)
	{
		this.to = to;
	}
}
