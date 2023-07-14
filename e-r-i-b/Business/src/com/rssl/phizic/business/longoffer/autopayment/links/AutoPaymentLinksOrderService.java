package com.rssl.phizic.business.longoffer.autopayment.links;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.resources.external.AutoPaymentLink;
import com.rssl.phizic.business.resources.external.AutoPaymentLinkOrder;
import com.rssl.phizic.business.resources.external.LongOfferLink;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Сервис для получения и сохранения списка линков автоплатежей с порядком их отображения
 * @ author gorshkov
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 */
public class AutoPaymentLinksOrderService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * получить линки автоплатежей по логину
	 * @param login логин клиента
	 * @return список линков автоплатежей
	 * @throws BusinessException
	 */
	public List<AutoPaymentLinkOrder> findByLogin(final CommonLogin login) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<AutoPaymentLinkOrder>>()
			{
				public List<AutoPaymentLinkOrder> run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.resources.external.AutoPaymentLinkOrder.list");
					query.setParameter("login", login.getId());
					return query.list();
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Сохраняем или обновляем линк автоплатежа
	 * @param autoPaymentLink линк атоплатежа
	 * @throws BusinessException
	 */
	public void addOrUpdateAutoPaymentOrder(AutoPaymentLinkOrder autoPaymentLink) throws BusinessException
	{
		simpleService.addOrUpdate(autoPaymentLink);
	}
}
