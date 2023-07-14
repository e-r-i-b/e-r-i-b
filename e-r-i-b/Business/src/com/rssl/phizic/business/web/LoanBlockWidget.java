package com.rssl.phizic.business.web;

import java.util.Collections;
import java.util.List;

/** ������ "���� �������"
 * @author gulov
 * @ created 23.07.2012
 * @ $Authors$
 * @ $Revision$
 */
public class LoanBlockWidget extends ProductWidgetBase
{
	//�������� ���������� ��� ���
	private boolean notify = true;
	//�� ������� ���� ���������
	private Integer loanNotifyDayCount = 5;
	//������ id ��������, �� ������� �� ����� ���������
	private List<Long> notNotifiedLoanLinkIds = Collections.emptyList();
	//��������� �� ������������ �������
	private transient boolean blinkingStoppedByUser = false;

	public boolean isNotify()
	{
		return notify;
	}

	public void setNotify(boolean notify)
	{
		this.notify = notify;
	}

	public Integer getLoanNotifyDayCount()
	{
		return loanNotifyDayCount;
	}

	public void setLoanNotifyDayCount(Integer loanNotifyDayCount)
	{
		this.loanNotifyDayCount = loanNotifyDayCount;
	}

	public List<Long> getNotNotifiedLoanLinkIds()
	{
		return notNotifiedLoanLinkIds;
	}

	public void setNotNotifiedLoanLinkIds(List<Long> notNotifiedLoanLinkIds)
	{
		this.notNotifiedLoanLinkIds = notNotifiedLoanLinkIds;
	}

	public boolean isBlinkingStoppedByUser()
	{
		return blinkingStoppedByUser;
	}

	public void setBlinkingStoppedByUser(boolean blinkingStoppedByUser)
	{
		this.blinkingStoppedByUser = blinkingStoppedByUser;
	}

	@Override
	protected Widget clone()
	{
		LoanBlockWidget newWidget = (LoanBlockWidget) super.clone();
		newWidget.setNotNotifiedLoanLinkIds(notNotifiedLoanLinkIds);
		return newWidget;
	}
}
