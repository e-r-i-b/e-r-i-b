package com.rssl.phizic.business.dictionaries.paymentsgrouprisk;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.limits.link.LimitPaymentsLinkBase;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * @author vagin
 * @ created 31.05.2012
 * @ $Author$
 * @ $Revision$
 * Сервис для работы с группами риска по типу платежа.
 */
public class PaymentsGroupRiskService
{
	private static final SimpleService service = new SimpleService();

	/**
	 * Поиск групп риска для типов платежей по переданному списку типов платежей.
	 * @param paymentTypes - список типов платежей
	 * @return список групприска по типам платежа.
	 * @throws BusinessException
	 */
	public List<LimitPaymentsLinkBase> getGroupsRiskByPaymentTypes(List<String> paymentTypes, Long departmentId) throws BusinessException
	{
		return service.find(DetachedCriteria.forClass(LimitPaymentsLinkBase.class).add(Expression.in("paymentType", paymentTypes)).add(Expression.eq("departmentId",departmentId)));
	}

	/**
	 * Поиск группы риска по типу платежа
	 * @param paymentType - тип платежа
	 * @return группа риска для переданого типа платежа
	 * @throws BusinessException
	 */
	public LimitPaymentsLinkBase getGroupsRiskByPaymentType(String paymentType, Long departmentId) throws BusinessException
	{
		return service.findSingle(DetachedCriteria.forClass(LimitPaymentsLinkBase.class).add(Expression.eq("paymentType", paymentType)).add(Expression.eq("departmentId",departmentId)));
	}
}
