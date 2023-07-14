package com.rssl.phizic.business.autoSubscription;

import com.rssl.phizic.business.longoffer.mock.MockLongOffer;
import com.rssl.phizic.common.types.LongOfferPayDay;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;

/**
 * @author bogdanov
 * @ created 27.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class MockAutoSubscription extends MockLongOffer implements AutoSubscription
{
	public String getReasonDesc()
	{
		return "";
	}

	public MockAutoSubscription()
	{
		super();
	}

	public MockAutoSubscription(String externalId)
	{
		super(externalId);
	}

	public Calendar getNextPayDate()
	{
		return DateHelper.getCurrentDate();
	}

	public AutoPayStatusType getAutoPayStatusType()
	{
		return AutoPayStatusType.Closed;
	}

	public void setAutoPayStatusType(AutoPayStatusType autoPayStatusType)
	{
	}

	public String getDocumentNumber()
	{
		return null;
	}

	public String getReceiverCard()
	{
		return null;
	}

	public String getAutopayNumber()
	{
		return null;
	}

	public void setAutopayNumber(String number) {}

	public LongOfferPayDay getLongOfferPayDay()
	{
		return null;
	}

	public Calendar getUpdatedDate()
	{
		return null;
	}

	public Money getMaxSumWritePerMonth()
	{
		return null;
	}
}
