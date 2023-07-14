package com.rssl.phizic.web.client.component;

import com.rssl.phizic.operations.widget.LoanDescriptor;

import java.util.List;

/**
 * ����� ������� "���� �������"
 * @author gulov
 * @ created 23.07.2012
 * @ $Authors$
 * @ $Revision$
 */
public class LoanBlockWidgetForm extends ProductBlockWidgetForm
{	
	private List<LoanDescriptor> loanDescriptors;
	private boolean isAllLoanDown;
	//����������� �� ������� �������������
	private boolean blinkingStopped;
	//"���������" �� ������. ������ ��������� �����, ����� ���� ���� �� ���� ������, ��� �������� �������� ���������� � ���� ��� ���������� �������� ��������
	private boolean widgetOverdue;

	private List<Long> showLoanLinkIds;

	public boolean isAllLoanDown()
	{
		return isAllLoanDown;
	}

	public void setAllLoanDown(boolean allLoanDown)
	{
		isAllLoanDown = allLoanDown;
	}

	public List<LoanDescriptor> getLoanDescriptors()
	{
		return loanDescriptors;
	}

	public void setLoanDescriptors(List<LoanDescriptor> loanDescriptors)
	{
		this.loanDescriptors = loanDescriptors;
	}

	public boolean isBlinkingStopped()
	{
		return blinkingStopped;
	}

	public void setBlinkingStopped(boolean blinkingStopped)
	{
		this.blinkingStopped = blinkingStopped;
	}

	public boolean isWidgetOverdue()
	{
		return widgetOverdue;
	}

	public void setWidgetOverdue(boolean widgetOverdue)
	{
		this.widgetOverdue = widgetOverdue;
	}

	public List<Long> getShowLoanLinkIds()
	{
		return showLoanLinkIds;
	}

	public void setShowLoanLinkIds(List<Long> showLoanLinkIds)
	{
		this.showLoanLinkIds = showLoanLinkIds;
	}
}
