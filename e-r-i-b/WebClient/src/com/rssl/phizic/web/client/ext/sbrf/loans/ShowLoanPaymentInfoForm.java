package com.rssl.phizic.web.client.ext.sbrf.loans;

import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.gate.loans.ScheduleItem;
import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author mihaylov
 * @ created 29.07.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowLoanPaymentInfoForm extends ActionFormBase
{
	private Long id;
	private Long paymentNumber;

	// TODO временные поля, для получения графика платежей по кредиту
	private Long startNumber;
	private Long count;

	private LoanLink loanLink;
	private ScheduleItem scheduleItem;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getPaymentNumber()
	{
		return paymentNumber;
	}

	public void setPaymentNumber(Long paymentNumber)
	{
		this.paymentNumber = paymentNumber;
	}

	public Long getStartNumber()
	{
		return startNumber;
	}

	public void setStartNumber(Long startNumber)
	{
		this.startNumber = startNumber;
	}

	public Long getCount()
	{
		return count;
	}

	public void setCount(Long count)
	{
		this.count = count;
	}

	public LoanLink getLoanLink()
	{
		return loanLink;
	}

	public void setLoanLink(LoanLink loanLink)
	{
		this.loanLink = loanLink;
	}

	public ScheduleItem getScheduleItem()
	{
		return scheduleItem;
	}

	public void setScheduleItem(ScheduleItem scheduleItem)
	{
		this.scheduleItem = scheduleItem;
	}
}
