package com.rssl.phizic.operations.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;
import java.util.List;

/**
 * @author Gololobov
 * @ created 17.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class ShowCashAndCashlessVolumeOperation extends FinancesOperationBase
{
	/**
	 * ������ ��� ����������� ������� "���� �������� �������� � ��������� ���������� �������"
	 * @param filter - ������ �������
	 * @return List - ������ ��� ��������� ������� "���� �������� �������� � ��������� ���������� �������"
	 * @throws BusinessException
	 */
	public List<CashAndCashlessVolumeDescription> getCashOperationsToFinancialStream(FinanceFilterData filter) throws BusinessException
	{
		try
		{
			//�����, �� ������� �������� ������
			List<String> cards = filter.isOnlyOwnCards() ? getPersonOwnCards() : getPersonCards();

			//�������� ������� �������� ������� �� ���������
			Query query = createQuery("list");

			//���� ������ ������� (c 00:00:00)
			Calendar fromDate = DateUtils.truncate(filter.getFromDate(), Calendar.DATE);
			query.setParameter("fromDate",fromDate);

			//���� ��������� ������� (�� 23:59:59)
			Calendar toDate = filter.getToDate();
			toDate.setTime(DateUtils.addDays(toDate.getTime(),1));
			toDate.setTime(DateUtils.truncate(toDate.getTime(),Calendar.DATE));
			toDate.setTime(DateUtils.addSeconds(toDate.getTime(),-1));
			query.setParameter("toDate", toDate);

			//true - �����, false - ������
			query.setParameter("income",filter.isIncome() ? 1:0);
			//������ (���-�� ����) �� �������� ������������ ������ �������� �� ������ ����, ����� ����� ���-��
			//������ ���������� ������ �� "group_day_count" ����
			query.setParameter("everyDayLimit",EVERY_DAY_LIMIT);
			query.setParameter("groupDayCount",GROUP_DAY_COUNT);
			//������ ���� �� ������� ����� ���������� ��������
			query.setParameter("cardsNums", cards);
		    //�� ������������
			query.setParameter("loginId", getLogin().getId());

			List<CashAndCashlessVolumeDescription> cardOperationsList = query.executeList();

			return cardOperationsList;
		}
		catch (DataAccessException e)
		{
			throw new BusinessException("������ ��� �������� ������ �������� � ����������� ��������: ",e);
		}
	}
}
