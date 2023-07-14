package com.rssl.phizic.business.basket.invoice;

import com.rssl.phizic.common.types.basket.InvoiceStatus;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author osminin
 * @ created 08.06.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class InvoiceServiceBase
{
	/**
	 * Перевод всех "новых" инвойсов в рамках подписки в статус INACTIVE
	 * @param subscriptionId идентификаторр подписки
	 * @param session сессия
	 */
	protected void doInactive(Long subscriptionId, Session session)
	{
		Query query = session.getNamedQuery("com.rssl.phizic.business.basket.invoice.BusinessInvoiceService.inactivateAllByAutoSubId");
		query.setParameter("subscriptionId", subscriptionId);
		query.executeUpdate();
	}

	/**
	 * Обновить статус инвойса
	 * @param session сессия
	 * @param id идентификатор инвойса
	 * @param status статус
	 */
	protected void updateStateById(Session session, Long id, InvoiceStatus status)
	{
		Query query = session.getNamedQuery("com.rssl.phizic.business.basket.invoice.BusinessInvoiceService.setStateByID");
		query.setLong("id", id);
		query.setParameter("state", status.name());
		query.executeUpdate();
	}
}
