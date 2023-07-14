package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.web.component.WidgetForm;

import java.util.List;

/**
 * @author Gololobov
 * @ created 09.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoanNotificationWidgetForm extends WidgetForm
{
	//������ �������� �������
	private List<LoanLink> loans;
	//������ ��������� ��� �������
	private LoanLink widgetLoanLink;
	//�� ������� �� ��� �������� ��������� ������� �� ��������� ������� �� �������
	private Long loanNotifyDayCount;
	//������� �������� ���� �� ���������� �������
	private Long beforeNextPaymentDaysCount;

	private boolean alarm;

	public List<LoanLink> getLoans()
	{
		return loans;
	}

	public void setLoans(List<LoanLink> loans)
	{
		this.loans = loans;
	}

	public LoanLink getWidgetLoanLink()
	{
		return widgetLoanLink;
	}

	public void setWidgetLoanLink(LoanLink widgetLoanLink)
	{
		this.widgetLoanLink = widgetLoanLink;
	}

	public Long getLoanNotifyDayCount()
	{
		return loanNotifyDayCount;
	}

	public void setLoanNotifyDayCount(Long loanNotifyDayCount)
	{
		this.loanNotifyDayCount = loanNotifyDayCount;
	}

	public Long getBeforeNextPaymentDaysCount()
	{
		return beforeNextPaymentDaysCount;
	}

	public void setBeforeNextPaymentDaysCount(Long beforeNextPaymentDaysCount)
	{
		this.beforeNextPaymentDaysCount = beforeNextPaymentDaysCount;
	}

	public boolean isAlarm()
	{
		return alarm;
	}

	public void setAlarm(boolean alarm)
	{
		this.alarm = alarm;
	}
}
