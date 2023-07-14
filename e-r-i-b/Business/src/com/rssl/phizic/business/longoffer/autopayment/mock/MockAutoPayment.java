package com.rssl.phizic.business.longoffer.autopayment.mock;

import com.rssl.phizic.business.longoffer.mock.MockLongOffer;
import com.rssl.phizic.common.types.MockObject;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentStatus;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 10.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class MockAutoPayment extends MockLongOffer implements AutoPayment, MockObject
{
	private String cardNumber = EMPTY_STRING;
	private String codeService = EMPTY_STRING;
	private Money floorLimit = null;
	private String friendlyName = EMPTY_STRING;
	private AutoPaymentStatus reportStatus = null;
	private Calendar dateAccepted = null;
	private String requisite = EMPTY_STRING;
	private String receiverName = EMPTY_STRING;

	public MockAutoPayment()
	{
	}

	public MockAutoPayment(String externalId)
	{
		super(externalId);
	}

	public String getCodeService()
	{
		return codeService;
	}

	public void setCodeService(String codeService)
	{
		this.codeService = codeService;
	}

	public Money getFloorLimit()
	{
		return floorLimit;
	}

	public void setFloorLimit(Money floorLimit)
	{
		this.floorLimit = floorLimit;
	}

	public String getFriendlyName()
	{
		return friendlyName;
	}

	public void setFriendlyName(String friendlyName)
	{
		this.friendlyName = friendlyName;
	}

	public AutoPaymentStatus getReportStatus()
	{
		return reportStatus;
	}

	public void setReportStatus(AutoPaymentStatus reportStatus)
	{
		this.reportStatus = reportStatus;
	}

	public String getRequisite()
	{
		return requisite;
	}

	public void setRequisite(String requisite)
	{
		this.requisite = requisite;
	}

	public String getReceiverName()
	{
		return receiverName;
	}

	public void setReceiverName(String receiverName)
	{
		this.receiverName = receiverName;
	}

	public Calendar getDateAccepted()
	{
		return dateAccepted;
	}

	public void setDateAccepted(Calendar dateAccepted)
	{
		this.dateAccepted = dateAccepted;
	}
}
