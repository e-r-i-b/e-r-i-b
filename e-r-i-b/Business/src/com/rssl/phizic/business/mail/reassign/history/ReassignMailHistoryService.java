package com.rssl.phizic.business.mail.reassign.history;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * Сервис для работы с историей переназначений
 * @author komarov
 * @ created 15.10.13 
 * @ $Author$
 * @ $Revision$
 */

public class ReassignMailHistoryService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * Получает историю переназначений по идентификатору письма
	 * @param mailId идентификатор письма
	 * @return история переназначений
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
	 * Добавление записи о причине переназначения
	 * @param reassignMailReason причина переназначения
	 * @return причина переназначения
	 * @throws BusinessException
	 */
	public ReassignMailReason saveReassignMailReason(ReassignMailReason reassignMailReason) throws BusinessException
	{
		return simpleService.addOrUpdate(reassignMailReason);
	}
}
