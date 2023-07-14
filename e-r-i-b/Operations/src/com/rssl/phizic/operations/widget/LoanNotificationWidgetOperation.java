package com.rssl.phizic.operations.widget;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.web.LoanNotificationWidget;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Calendar;

/**
 * @author Gololobov
 * @ created 09.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoanNotificationWidgetOperation extends WidgetOperation<LoanNotificationWidget>
{
	private static final String DEFAULT_TITLE = "����������� �� ������ �������";

	private List<LoanLink> loanLinks;

	private LoanLink loanLink;

	private Long notifyDays;

	private Long daysLeft;

	private boolean alarm;

	@Override
	protected void initialize() throws BusinessException, BusinessLogicException
	{
		super.initialize();

		// 1. ������ ��������
		try
		{
			loanLinks = getPersonData().getLoans();
		}
		catch (BusinessLogicException e)
		{
			log.error("���� ��� ������� �������� ������ �������� ������� ��� ������� ����������� �� ������ �������", e);
			loanLinks = Collections.emptyList();
		}

		for (LoanLink link : loanLinks)
		{
			if (link.getLoan() instanceof StoredLoan)
			{
				setUseStoredResource(true);
				break;
			}
		}
	}

	@Override
	public void update() throws BusinessException
	{
		super.update();

		LoanNotificationWidget widget = getWidget();

		// 1. ��������� ������
		loanLink = null;
		if (CollectionUtils.isNotEmpty(loanLinks) && StringHelper.isNotEmpty(widget.getLoanAccountNumber()))
			loanLink = getPersonData().findLoan(widget.getLoanAccountNumber());

		// 2. �����
		notifyDays = null;
		try
		{
			if (loanLink != null)
				notifyDays = Long.valueOf(widget.getLoanNotifyDayCount());
		}
		catch (NumberFormatException ignored) {}

		// 3. ���-�� ���� �� �������� ������
		daysLeft = null;
		if (loanLink != null)
		{
			Loan loan = loanLink.getLoan();
			Calendar today = DateHelper.getCurrentDate();
			Calendar paymentDate = loan.getNextPaymentDate();
			if (paymentDate != null)
				daysLeft = DateHelper.daysDiff(today, paymentDate);
			if (daysLeft != null && daysLeft < 0)
				daysLeft = 0L;
		}

		// 4. ����������� �� ������
		if (notifyDays != null && daysLeft != null)
			alarm = (daysLeft <= notifyDays);

		// 5. �������� �������, ���� ���� �������������� � ��������� �������������� ����������
		if (alarm) {
			if (isAlarmSettingsChanged())
				widget.setBlinking(true);
		}
		else widget.setBlinking(false);
	}

	private boolean isAlarmSettingsChanged()
	{
		LoanNotificationWidget newWidget = getWidget();
		LoanNotificationWidget oldWidget = getOldWidget();

		return (!StringHelper.equalsNullIgnore(newWidget.getLoanAccountNumber(), oldWidget.getLoanAccountNumber()) ||
				!(newWidget.getLoanNotifyDayCount() == oldWidget.getLoanNotifyDayCount()));
	}

	/**
	 * @return ������ �������� �������
	 */
	public List<LoanLink> getLoanLinks()
	{
		return Collections.unmodifiableList(loanLinks);
	}

	/**
	 * @return ������ �������
	 */
	public LoanLink getLoanLink()
	{
		return loanLink;
	}

	/**
	 * �� ������� �� ��� �������� ��������� ������� �� ��������� ������� �� �������
	 * @return
	 */
	public Long getNotifyDays()
	{
		return notifyDays;
	}

	public Long getDaysLeft()
	{
		return daysLeft;
	}

	public boolean isAlarm()
	{
		return alarm;
	}

	public String getTitle()
	{
		return DEFAULT_TITLE;
	}
	
	@Override
	public boolean checkUseStoredResource()
	{
		if( super.checkUseStoredResource() )
		{
			throw new InactiveExternalSystemException(StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) loanLinks.get(0).getLoan()));
		}

		return false;
	}
}
