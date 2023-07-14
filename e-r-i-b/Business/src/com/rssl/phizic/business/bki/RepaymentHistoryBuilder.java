package com.rssl.phizic.business.bki;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizicgate.esberibgate.bki.generated.EnquiryResponseERIB;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

import java.util.*;

/**
 * @author Gulov
 * @ created 21.10.14
 * @ $Author$
 * @ $Revision$
 */
class RepaymentHistoryBuilder
{
	private static final List<String> normalCodeStates = new ArrayList<String>(7);
	private static final List<String> criticalCodeStates = new ArrayList<String>(3);

	static
	{
		/**
		 * (�������, ���������� ���� ��������)
		 */
		Collections.addAll(normalCodeStates, "A","1","2","3","4","5","6");

		/**
		 * ��������� ����������� ������ (��������, �����, ������)
		 */
		Collections.addAll(criticalCodeStates, "8","L","W");
	}

	private static class MonthComparator implements Comparator<CreditDetailRepaymentMonth>
	{
		public int compare(CreditDetailRepaymentMonth o1, CreditDetailRepaymentMonth o2)
		{
			return o2.getMonthNumber() - o1.getMonthNumber();
		}
	}

	private static class YearComparator implements Comparator<CreditDetailRepaymentYear>
	{
		public int compare(CreditDetailRepaymentYear o1, CreditDetailRepaymentYear o2)
		{
			return o2.getYear() - o1.getYear();
		}
	}

	/**
	 * ���������� ������� ������ �� ������� (�����)
	 * @param record ���������� � ������� (�����) �� ������
	 * @return ������ ������� ������
	 * @throws BusinessException
	 */
	RepaymentHistory build(EnquiryResponseERIB.Consumers.S.CAIS record) throws BusinessException
	{
		List<PaymentState> paymentStates = new LinkedList<PaymentState>();
		List<PaymentState> criticalStates = new LinkedList<PaymentState>();
		MultiMap map = new MultiValueMap();
		Calendar minDate = CreditBuilderFormatter.formatDate(record.getMontlyHistories().get(0).getHistoryDate());
		Calendar maxDate = CreditBuilderFormatter.formatDate(record.getMontlyHistories().get(0).getHistoryDate());
		List<CreditDetailRepaymentMonth> months = new LinkedList<CreditDetailRepaymentMonth>();
		// ���� �� ������� ������
		// ���������� ������� � ���������������� ����
		// ���������� ������������ � ����������� ���� ������
		for (EnquiryResponseERIB.Consumers.S.CAIS.MontlyHistory history : record.getMontlyHistories())
		{
			Calendar historyDate = CreditBuilderFormatter.formatDate(history.getHistoryDate());
			maxDate = DateHelper.maxOfDates(maxDate, historyDate);
			minDate = minDate.after(historyDate) ? historyDate : minDate;
			int year = CreditBuilderFormatter.getYear(history.getHistoryDate());
			PaymentState state = CreditBuilderFormatter.getPaymentState(history.getAccountPaymentHistory());
			// �������� ������� � ��������� �� �����
			Money instalment = CreditBuilderFormatter.formatAmount(history.getInstalment(), record.getCurrency());
			instalment.setMandatoryField(true);
			CreditDetailRepaymentMonth item = new CreditDetailRepaymentMonth(year,
					CreditBuilderFormatter.getMonthNumber(history.getHistoryDate()),
					CreditBuilderFormatter.getMonthShortString(history.getHistoryDate()),
					state.getState(),
					CreditBuilderFormatter.formatAmount(history.getAccountBalance(), record.getCurrency()),
					CreditBuilderFormatter.formatAmount(history.getArrearsBalance(), record.getCurrency()),
					instalment
			);
			months.add(item);
			// ��������� � ���� �� ������������ ���
			map.put(year, item);
			// ���������� ������� ������, ��� ����������� �� �� �����
			if (normalCodeStates.contains(state.getState().getCode()))
				putPaymentState(paymentStates, state);
			else if (criticalCodeStates.contains(state.getState().getCode()))
				putPaymentState(criticalStates, state);
		}
		Calendar temp;
		// ��������� ����������� ������ � ���������� �� ������������ � ����������� ���� ������
		// ����� ��� ����������� �� � ����������, ������ � ����������, ��� ����������� javascriptom ������� ��� ������ �� ������� (� ���������� ��������)
		while (minDate.before(maxDate))
		{
			temp = Calendar.getInstance();
			temp = DateHelper.clearTime(temp);
			temp.set(Calendar.YEAR, minDate.get(Calendar.YEAR));
			temp.set(Calendar.MONTH, minDate.get(Calendar.MONTH));
			temp.set(Calendar.DATE, 1);
			if (!findMonth(months, temp))
				map.put(minDate.get(Calendar.YEAR),
					new CreditDetailRepaymentMonth(minDate.get(Calendar.YEAR), minDate.get(Calendar.MONTH),
						CreditBuilderFormatter.getMonthShortByNumber(minDate.get(Calendar.MONTH)),
						null, new Money(null, "RUB"), new Money(null, "RUB"), new Money(null, "RUB")));
			minDate.add(Calendar.MONTH, 1);
		}
		List<CreditDetailRepaymentYear> history = new LinkedList<CreditDetailRepaymentYear>();
		//noinspection unchecked
		Set<Map.Entry<Object, Object>> set = map.entrySet();
		MonthComparator monthComparator = new MonthComparator();
		// ��������� ������� �� ����� � �������
		for (Map.Entry<Object, Object> entry : set)
		{
			//noinspection unchecked
			List<CreditDetailRepaymentMonth> list = (List<CreditDetailRepaymentMonth>) entry.getValue();
			int year = (Integer) entry.getKey();
			Collections.sort(list, monthComparator);
			history.add(new CreditDetailRepaymentYear(year, list));
		}
		Collections.sort(history, new YearComparator());
		RepaymentHistory repaymentHistory = new RepaymentHistory(history, paymentStates, criticalStates);

		return repaymentHistory;
	}

	private boolean findMonth(List<CreditDetailRepaymentMonth> list, Calendar date)
	{
		for (CreditDetailRepaymentMonth repaymentMonth : list)
		{
			if (repaymentMonth.getYear() == date.get(Calendar.YEAR)
				&& repaymentMonth.getMonthNumber() == date.get(Calendar.MONTH) + 1)
				return true;
		}
		return false;
	}

	private void putPaymentState(final List<PaymentState> paymentStates, PaymentState state)
	{
		for (PaymentState paymentState : paymentStates)
			if (paymentState.getState().getCode().equals(state.getState().getCode()))
			{
				paymentState.add();
				return;
			}
		state.add();
		paymentStates.add(state);
	}
}
