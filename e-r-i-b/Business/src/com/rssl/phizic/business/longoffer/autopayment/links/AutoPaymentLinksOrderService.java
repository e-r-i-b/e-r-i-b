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
 * ������ ��� ��������� � ���������� ������ ������ ������������ � �������� �� �����������
 * @ author gorshkov
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 */
public class AutoPaymentLinksOrderService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * �������� ����� ������������ �� ������
	 * @param login ����� �������
	 * @return ������ ������ ������������
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
	 * ��������� ��� ��������� ���� �����������
	 * @param autoPaymentLink ���� ����������
	 * @throws BusinessException
	 */
	public void addOrUpdateAutoPaymentOrder(AutoPaymentLinkOrder autoPaymentLink) throws BusinessException
	{
		simpleService.addOrUpdate(autoPaymentLink);
	}
}
