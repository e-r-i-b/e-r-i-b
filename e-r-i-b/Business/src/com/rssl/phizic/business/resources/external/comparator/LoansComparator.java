package com.rssl.phizic.business.resources.external.comparator;

import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.loans.*;

import java.util.Comparator;

/**
 * @author kuzmin
 * @ created 10.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class LoansComparator  extends ProductComparator  implements Comparator<Pair<LoanLink,ScheduleAbstract>>
{
	public int compare(Pair<LoanLink,ScheduleAbstract> o1, Pair<LoanLink,ScheduleAbstract> o2)
	{
		Loan loan1 = o1.getFirst().getLoan();
		Loan loan2 = o2.getFirst().getLoan();

		if (loan1.getState() == null || loan2.getState() == null)
		{
			int result = compareToNull(loan1.getState(), loan2.getState());
			if (result != 0)
				return result;
		}

		//приоритет у кредита со статусом "просрочен"
		if (LoanState.overdue == loan1.getState() && LoanState.overdue != loan2.getState())
		{
			return -1;
		}

		if (LoanState.overdue != loan1.getState() && LoanState.overdue == loan2.getState())
		{
			return 1;
		}

		ScheduleAbstract abs1 = o1.getSecond();
		ScheduleAbstract abs2 = o2.getSecond();

		//приоритет у кредита, по которому получили выписку
		if (abs1 == null || abs2 == null)
		{
			int result = compareToNull(abs2,abs1);
			if (result != 0)
				return result;
		}
		else
		{
			//приоритет у кредита, по которому получили график погашения
			if (abs1.getSchedules() == null || abs2.getSchedules() == null)
			{
				int result = compareToNull(abs2.getSchedules(),abs1.getSchedules());
				if (result != 0)
					return result;
			}
			else
			{
				//приоритет у кредита с наибольшей суммой просроченной задолженности
				if (!abs1.getSchedules().isEmpty() &&  !abs2.getSchedules().isEmpty())
				{
					DateDebtItem amount1 = abs1.getSchedules().get(0).getPenaltyDateDebtItemMap().get(PenaltyDateDebtItemType.delayedDebtAmount);
					DateDebtItem amount2 = abs2.getSchedules().get(0).getPenaltyDateDebtItemMap().get(PenaltyDateDebtItemType.delayedDebtAmount);

					if(amount1 == null || amount2 == null)
					{
						int result = compareToNull(amount2, amount1);
						if (result != 0)
							return result;
					}
					else
					{
						int result = amount2.getAmount().compareTo(amount1.getAmount());
						if (result != 0)
							return result;
					}
				}
				else  if(abs1.getSchedules().isEmpty() &&  !abs2.getSchedules().isEmpty())
				{
					return 	1;
				}
				else  if(!abs1.getSchedules().isEmpty() &&  abs2.getSchedules().isEmpty())
				{
					return 	-1;
				}
			}
		}

		if (loan1.getTermStart() == null || loan2.getTermStart() == null)
		{
			int result = compareToNull(loan1.getTermStart(), loan2.getTermStart());
			if (result != 0)
				return result;
		}
		else
		{
			//приоритет у кредита с более поздней датой заключения договора
			int result = loan2.getTermStart().compareTo(loan1.getTermStart());
			if (result != 0)
				return result;
		}

		if (loan1.getLoanAmount() == null || loan2.getLoanAmount() == null)
		{
			return compareToNull(loan1.getTermStart(), loan2.getTermStart());
		}

		//приоритет у кредита с наибольшей суммой договора
		return loan2.getLoanAmount().compareTo(loan1.getLoanAmount());
	}
}
