package com.rssl.phizic.operations.loans.loan;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.gate.loans.LoansService;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.loans.ScheduleItem;
import com.rssl.phizic.gate.loans.ScheduleAbstract;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.business.operations.restrictions.LoanRestriction;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.common.types.exceptions.IKFLException;

import java.util.List;

/**
 * @author gladishev
 * @ created 26.03.2008
 * @ $Author$
 * @ $Revision$
 */

public class GetLoanInfoOperation extends OperationBase<LoanRestriction> implements ViewEntityOperation<Loan>
{
	private final LoansService loanService = GateSingleton.getFactory().service(LoansService.class);
	private Loan loan;
	private ScheduleItem scheduleItem;
	private List<ScheduleItem> schedule;
	private ActivePerson person;

	public void initialize(String loanId) throws BusinessException
	{
		try
		{
			Loan temp = GroupResultHelper.getOneResult(loanService.getLoan(loanId));
			if (getRestriction().accept(temp))
				loan = temp;
			else
				throw new RestrictionViolationException("Кредит с id = " + loanId);

			scheduleItem = loanService.getNextScheduleItem(loan);
			ScheduleAbstract scheduleAbstract = GroupResultHelper.getOneResult(loanService.getSchedule(null, null, loan));
			schedule = scheduleAbstract.getSchedules();
			person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		}
		catch(IKFLException e)
		{
			throw new BusinessException("Ошибка при получении списка кредитов клиента", e);
		}
	}
	public ScheduleItem getScheduleItem()
	{
		return scheduleItem;
	}

	public List<ScheduleItem> getSchedule()
	{
		return schedule;
	}

	public ActivePerson getPerson()
	{
		return person;
	}

	public Loan getEntity() throws BusinessException
	{
		return loan;
	}
}
