package com.rssl.phizic.web.client.bki;

import com.rssl.phizic.business.bki.CreditDetail;
import com.rssl.phizic.business.bki.Money;
import com.rssl.phizic.business.bki.PersonCreditProfile;
import com.rssl.phizic.business.bki.PersonCreditReport;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author Gulov
 * @ created 20.10.14
 * @ $Author$
 * @ $Revision$
 */
public class CreditReportForm extends EditFormBase
{
	private Money cost;
	private Long providerId;
	private PersonCreditReport report;
	private Boolean activeCreditViewBlock;
	private boolean waitingNew;
	private boolean bkiError;
	private Integer creditId;
	private PersonCreditProfile profile;
	private CreditDetail creditDetail;

	public void setReport(PersonCreditReport report)
	{
		this.report = report;
	}

	public PersonCreditReport getReport()
	{
		return report;
	}

	public Money getCost()
	{
		return cost;
	}

	public void setCost(Money cost)
	{
		this.cost = cost;
	}

	public void setProviderId(Long providerId)
	{
		this.providerId = providerId;
	}

	public Long getProviderId()
	{
		return providerId;
	}

	public Boolean getActiveCreditViewBlock()
	{
		return activeCreditViewBlock;
	}

	public void setActiveCreditViewBlock(Boolean activeCreditViewBlock)
	{
		this.activeCreditViewBlock = activeCreditViewBlock;
	}

	public Integer getCreditId()
	{
		return creditId;
	}

	public void setCreditId(Integer creditId)
	{
		this.creditId = creditId;
	}

	public PersonCreditProfile getProfile()
	{
		return profile;
	}

	public void setProfile(PersonCreditProfile profile)
	{
		this.profile = profile;
	}

	public void setCreditDetail(CreditDetail creditDetail)
	{
		this.creditDetail = creditDetail;
	}

	public CreditDetail getCreditDetail()
	{
		return creditDetail;
	}

	public boolean getWaitingNew()
	{
		return waitingNew;
	}

	public void setWaitingNew(boolean waitingNew)
	{
		this.waitingNew = waitingNew;
	}

	public boolean getBkiError()
	{
		return bkiError;
	}

	public void setBkiError(boolean bkiError)
	{
		this.bkiError = bkiError;
	}
}
