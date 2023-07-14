package com.rssl.phizic.operations.widget;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.OperationFactory;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.business.web.LoanBlockWidget;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.loans.ScheduleAbstract;
import com.rssl.phizic.operations.loans.loan.GetLoanAbstractOperation;
import com.rssl.phizic.operations.loans.loan.GetLoanListOperation;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/** Операция виджета "Ваши кредиты"
 * @author gulov
 * @ created 23.07.2012
 * @ $Authors$
 * @ $Revision$
 */
public class LoanBlockWidgetOperation extends ProductBlockWidgetOperationBase<LoanBlockWidget>
{
	private GetLoanListOperation loansOperation;
	private GetLoanAbstractOperation loanAbstractOperation;
	private List<LoanLink> allLoanLinks;
	private boolean widgetOverdue;

	@Override
	protected void initialize() throws BusinessException, BusinessLogicException
	{
		super.initialize();

		OperationFactory factory = getOperationFactory();

		loansOperation = factory.create(GetLoanListOperation.class);
		allLoanLinks = loansOperation.getAllLoans();

		loanAbstractOperation = factory.create(GetLoanAbstractOperation.class);
		List<LoanLink> showOperationLoanLinks = loansOperation.getShowOperationLinks(allLoanLinks);
		loanAbstractOperation.initialize(showOperationLoanLinks);

		for (LoanLink link : allLoanLinks)
		{
			if (link.getLoan() instanceof AbstractStoredResource)
			{
				setUseStoredResource(true);
				break;
			}
		}
		
		actualizeHiddenProducts(allLoanLinks);
	}

	public List<LoanLink> getLoanLinks()
	{
		return Collections.unmodifiableList(allLoanLinks);
	}

	public boolean isAllLoanDown()
	{
		return CollectionUtils.isEmpty(allLoanLinks) && (loanAbstractOperation.isError() || loansOperation.isBackError());
	}

	public List<LoanDescriptor> getLoanDescriptors(Long count) throws BusinessException
	{
		Pair<Map<LoanLink, ScheduleAbstract>, Map<LoanLink, String>> scheduleAbstract = loanAbstractOperation.getScheduleAbstract(-count, count, true, false);
		Map<LoanLink, ScheduleAbstract> abstractMap = scheduleAbstract.getFirst();
		Long notifyDays = Long.valueOf(getWidget().getLoanNotifyDayCount());
		List<Long> notNotifiedLoanLinkIds = getWidget().getNotNotifiedLoanLinkIds();

		List<LoanDescriptor> loanDescriptors = new LinkedList<LoanDescriptor>();
		widgetOverdue = false;
		for(LoanLink loanLink : allLoanLinks)
		{
			ScheduleAbstract shedule = abstractMap.get(loanLink);

			Boolean overdue = false;
			Long daysLeft =null;
			Loan loan = loanLink.getLoan();
			Calendar today = DateHelper.getCurrentDate();
			Calendar paymentDate = loan.getNextPaymentDate();
			if (paymentDate != null)
				daysLeft = DateHelper.daysDiff(today, paymentDate);
			if (daysLeft != null && daysLeft < 0)
				daysLeft = 0L;
			if (notifyDays != null && daysLeft != null)
				overdue = (daysLeft <= notifyDays);

			if(overdue && !notNotifiedLoanLinkIds.contains(loanLink.getId()))
				widgetOverdue = true;

			LoanDescriptor loanDescriptor = new LoanDescriptor();
			loanDescriptor.setLoanLink(loanLink);
			loanDescriptor.setScheduleAbstract(shedule);
			loanDescriptor.setOverdue(overdue);
			loanDescriptor.setDaysLeft(daysLeft);

			loanDescriptors.add(loanDescriptor);
		}
		return loanDescriptors;
	}

	@Override
	public boolean checkUseStoredResource()
	{
		if( super.checkUseStoredResource() )
		{
			throw new InactiveExternalSystemException(StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) allLoanLinks.get(0).getLoan()));
		}
		
		return false;
	}

	/**
	 * устанавливает поле виджета "пользователь остановил мигание"
	 * @param stop - true, если остановил
	 */
	public void stopBlinking(Boolean stop)
	{
		getWidget().setBlinkingStoppedByUser(stop);
	}

	public boolean isWidgetOverdue()
	{
		return widgetOverdue;
	}
}
