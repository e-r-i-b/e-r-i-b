package com.rssl.phizic.business.mail.reassign.history;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * ������ ��� ������ � �������� ��������������
 * @author komarov
 * @ created 15.10.13 
 * @ $Author$
 * @ $Revision$
 */

public class ReassignMailHistoryService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * �������� ������� �������������� �� �������������� ������
	 * @param mailId ������������� ������
	 * @return ������� ��������������
	 * @throws BusinessException
	 */
	public List<ReassignMailReason> getReassignMailHistoryByMailId(Long mailId) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ReassignMailReason.class).
			add(Expression.eq("mailId", mailId)).
			addOrder(Order.desc("id"));
		return simpleService.find(criteria);
	}

	/**
	 * ���������� ������ � ������� ��������������
	 * @param reassignMailReason ������� ��������������
	 * @return ������� ��������������
	 * @throws BusinessException
	 */
	public ReassignMailReason saveReassignMailReason(ReassignMailReason reassignMailReason) throws BusinessException
	{
		return simpleService.addOrUpdate(reassignMailReason);
	}
}
