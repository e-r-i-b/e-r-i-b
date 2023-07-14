package com.rssl.phizic.business.web;

import java.util.Collections;
import java.util.List;

/** Виджет "Ваши кредиты"
 * @author gulov
 * @ created 23.07.2012
 * @ $Authors$
 * @ $Revision$
 */
public class LoanBlockWidget extends ProductWidgetBase
{
	//включено оповещение или нет
	private boolean notify = true;
	//за сколько дней оповещать
	private Integer loanNotifyDayCount = 5;
	//список id кредитов, по которым не нужно оповещать
	private List<Long> notNotifiedLoanLinkIds = Collections.emptyList();
	//остановил ли пользователь мигание
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
