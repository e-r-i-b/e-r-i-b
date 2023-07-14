package com.rssl.phizic.business.accounts;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.calendar.CalendarService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.payments.AccountClosingPaymentToAccount;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * @author Balovtsev
 * @version 14.10.13 16:40
 */
public class AccountsHelper
{
	/**
	 * ���������, ��������� �� ��� ���������� ������ ���������� ��������
	 *
 	 * @param  account ����������� ����
	 * @return true - ���������, false - ���������
	 */
	public final static boolean isPermittedForFinancialTransactions(Account account)
	{
		for (NotPermittedAccountsForFinancialTransactions values : NotPermittedAccountsForFinancialTransactions.values())
		{
			if (values.kind.equals(account.getKind()) && values.subKing.equals(account.getSubKind()))
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * ���������, �������� �� ������� ����� �������� ������ � �������� ���
	 *
 	 * @param closeDate ���� �������� ������
	 * @return ������� true - ���� �������� � ��������, false - � ��������� ������
	 */
	public final static boolean isTodayCloseDayAndHoliday(Calendar closeDate) throws BusinessException
    {
        CalendarService service = new CalendarService();

        if (closeDate != null) {
            Department department = PersonContext.getPersonDataProvider().getPersonData().getDepartment();
            return DateHelper.isEqualDate(closeDate, Calendar.getInstance()) && service.isHoliday(closeDate, department, AccountClosingPaymentToAccount.class);
        }

        return false;
	}

	/**
	 * ��������� ��� � ������ ������ ��� ������� �� �������� ���������� ���������� ��������(��������, ������� � �.�).
	 */
	private enum NotPermittedAccountsForFinancialTransactions
	{
		KIND76_SUBKIND2 (76L, 2L),
		KIND77_SUBKIND41(77L, 41L),
		KIND31_SUBKIND11(31L, 11L);

		private Long kind;
		private Long subKing;

		NotPermittedAccountsForFinancialTransactions(Long kind, Long subKind)
		{
			this.kind    = kind;
			this.subKing = subKind;
		}
	}
}
