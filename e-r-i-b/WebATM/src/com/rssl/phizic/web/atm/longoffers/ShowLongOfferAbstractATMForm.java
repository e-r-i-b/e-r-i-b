package com.rssl.phizic.web.atm.longoffers;

import com.rssl.phizic.web.common.client.longoffers.ShowLongOfferInfoForm;

/**
 * Form ��� ����������� ������� ������������ ���� LongOffer � ��������� ������
 * @ author Rtischeva
 * @ created 07.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class ShowLongOfferAbstractATMForm extends ShowLongOfferInfoForm
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

