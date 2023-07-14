package com.rssl.phizic.business.shop;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.providers.InternetShopsServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentToOrder;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.person.Person;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Date;
import java.util.List;

import static com.rssl.common.forms.FormConstants.FNS_PAYMENT_FORM;

/**
 * ��������, �������� ��� ������ � �������� ������������.
 *
 * @author Mescheryakova
 * @ created 03.12.2010
 * @ $Author$
 * @ $Revision$
 */
public class ExternalPaymentService
{
	private final SimpleService simpleService = new SimpleService();
	private final ServiceProviderService providerService = new ServiceProviderService();

	private static ExternalPaymentService it;
	private static final Object LOCK = new Object();

	private ExternalPaymentService() {}

	public static ExternalPaymentService get()
	{
		if (it != null)
			return it;

		synchronized (LOCK)
		{
			if (it != null)
				return it;

			it = new ExternalPaymentService();
			return it;
		}
	}

	public SimpleService getSimpleService()
	{
		return simpleService;
	}

	/**
	 * ���������� ���������� �� ����� ����������.
	 * @param systemName �������� ����������.
	 * @return ���������.
	 */
	public InternetShopsServiceProvider getRecipientIdBySystemName(String systemName) throws BusinessException
	{
		return providerService.getRecipientActivityBySystemName(systemName);
	}

	/**
	 * ���������� ���������� �� �������������� ������.
	 * @param uuid ������������� ������.
	 * @return ���������.
	 */
	public InternetShopsServiceProvider getRecipientByOrderUuid(String uuid) throws BusinessException
	{
		Order order = getOrder(uuid);
		if (order == null)
			return null;

		return getRecipientIdBySystemName(order.getSystemName());
	}

	/**
	 * �������� ���������� ���������� �� ��� ���������� ��� �������� ����� � �������
	 * @param person - ������� ������������
	 * @return ����� ���������� ���������� ��� ��� �������� ����� � �������
	 */
	public static int getAllCountFnsOrdersForPerson(final Person person) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Long>()
			{
				public Long run(Session session) throws Exception
				{
					return (Long) session.getNamedQuery("com.rssl.phizic.business.shop.countOfActiveFnsPayment")
							.setParameter("person_id", person.getId())
							.setParameter("login_date", person.getLogin().getLogonDate())
							.uniqueResult();
				}
			}).intValue();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������������� ���������� �� ������ �� ���
	 * @param fns - ���� ������
	 * @throws BusinessException - ������ ���������� � ��, ������ ��������� ����������� ����
	 */
	public OrderInfo registerGeneralInfo(FNS fns)   throws BusinessException
	{
		InternetShopsServiceProvider provider = getRecipientIdBySystemName(fns.getOrder().getSystemName());
		if (provider == null || !FNS_PAYMENT_FORM.equals(provider.getFormName()))
			throw new BusinessException("������� ���������  ����� �� ��� � ���������� ��� ����������� ����������");

		return registerGeneralInfo((OrderInfo) fns);
	}

	/**
	 * ���������������� ���������� �� ������ �� ���
	 * @param uecOrder - �����
	 * @throws BusinessException - ������ ���������� � ��, ������ ��������� ����������� ����
	 */
	public Order registerUECOrder(Order uecOrder) throws BusinessException
	{
		try
		{
			// ��������� ������
			return getSimpleService().addOrUpdateWithConstraintViolationException(uecOrder);
		}
		catch(BusinessException e)
		{
			throw new BusinessException("������ ��� ����������� ���������� �� ������ ���",  e);
		}

	}

	/**
	 * ���������������� ���������� �� ������
	 * @param info - ���� ������
	 * @return
	 * @throws BusinessException - ������ ���������� � ��, ������ ��������� ����������� ����
	 */
	private OrderInfo registerGeneralInfo(OrderInfo info) throws BusinessException
	{
		try
		{
			// ��������� ������
			return getSimpleService().addOrUpdateWithConstraintViolationException(info);
		}
		catch (ConstraintViolationException e)
		{
			Order order = info.getOrder();
			throw new BusinessException("������ ��� ����������� ���������� �� ������: ����� � ������ ����������� " +
					"(extendedId=" + order.getExtendedId() + ", systemName=" + order.getSystemName() + ") " +
					"��� ��� ��������������� �����",  e);
		}
		catch(BusinessException e)
		{
			throw new BusinessException("������ ��� ����������� ���������� �� ������",  e);
		}
	}

	/**
	 * �������� ������ �� ������
	 * @param uuid  - ������������� �������
	 * @return null - ���� ������ �� �������; ����� ����������� ������
	 * @throws BusinessException: ������� null, ������� ������ 1-�� ������
	 */
	public OrderInfo getOrderInfo(String uuid)  throws BusinessException
	{
		if (uuid == null)
			throw new BusinessException("����������� ����� uuid="+uuid);

		DetachedCriteria dc = DetachedCriteria.forClass(OrderInfo.class);
		dc.createAlias("order", "orderAlias");
		dc.add( Expression.eq("orderAlias.uuid", uuid));

		OrderInfo orderInfo =  getSimpleService().findSingle(dc);

		// ���� ��� ������� ��� ������� ��������, �� orderInfo ���, ������� ������ ��� �����
		if (orderInfo == null)
		{
			Order order = getOrder(uuid);

			if (order == null)
				throw new BusinessException("�� ������� ����� �������� ������ �� uuid=" + uuid);

			orderInfo = new WebShop();  // � ��� ��� ���� �������� ������, ������� ��� ������� � ���������� �������
			orderInfo.setOrder(order);
		}

		return orderInfo;
	}

	/**
	 * �������� �������� ���� ������ �� �������������� ������� (��� ����������� ���������� ��� ������: ��� ��� �������)
	 * @param uuid - ����� �������
	 * @return - ��������� �����
	 * @throws BusinessException
	 */
	public Order getOrder(String uuid) throws BusinessException
	{
		if (uuid == null)
			throw new BusinessException("����������� �����");

		// �������� ����� �� uuid
		DetachedCriteria dc = DetachedCriteria.forClass(Order.class);
		dc.add( Expression.eq("uuid", uuid));
		return  getSimpleService().findSingle(dc);
	}

	/**
	 * �������� ����� �� ����,  �����  ������� � ������ ������ �� ������� �������.
	 * @param extOrderNumber - ����� ������ �� ������� �������
	 * @param systemName - ��� �������
	 * @return - ��������� �����
	 * @throws BusinessException
	 */
	public Order getOrder(String extOrderNumber,String systemName) throws BusinessException
	{
		DetachedCriteria dc = DetachedCriteria.forClass(Order.class);
		dc.add( Expression.eq("extendedId", extOrderNumber));
		dc.add( Expression.eq("systemName", systemName));

		return  getSimpleService().findSingle(dc);
	}

	/**
	 * ��������� �� uuid, ����������� �� ������ ����� ����������� ������������
	 * @param uuid - ������������� �������� ������
	 * @param person - ������������
	 * @return true - �����������, false - ���
	 */
	public boolean isOrderForPerson(String uuid, Person person) throws BusinessException
	{
		DetachedCriteria dc = DetachedCriteria.forClass(Order.class);
		dc.add(Expression.eq("person.id", person.getId()));
		dc.add(Expression.eq("uuid", uuid));

		return (getSimpleService().findSingle(dc) != null);
	}

	/**
	 * �������� ���-������, � ���. ��� �������� ������ �� �������� "��������", "������", "�������" ,
	 * � ���������� �� �������� NOT_SEND(�� ����������), ERROR(���������� �� �������� ������)
	 * ������ NOT_SEND ��� ������� ��� ������������ � ������ �������� ������ ��������� � ������ "��������", "������" ��� "�������".
	 * @param maxRows  - ������������ ���-�� �����, ������� ����� �������, -1 = ��� �����������
	 * @param time
	 * @param notifyMaxCount - ������������ ���������� ������� �������� (�.�. �������������)
	 * @return ������ ������� �� ���-���������, � ������� ��� �������� ������, �� �� ���� ������� ����������
	 */
	public List<Order> getNotNotifiedUECOrders(final int maxRows, final Date time, final int notifyMaxCount) throws BusinessException
	{
		try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<List<Order>>()
		    {
		        public List<Order> run(Session session) throws Exception
		        {
		            Query query = session.getNamedQuery("com.rssl.phizic.business.shop.getNotNotifiedUECOrders");
			        query.setParameter("time", time);
			        query.setInteger("notifyMaxCount", notifyMaxCount);
			        if (maxRows != -1)
			            query.setMaxResults(maxRows);

		            return (List<Order>) query.list();
			    }

		    });
		}
		catch (Exception e)
		{
			throw new BusinessException("������ ��������� ���-�������, � ������� ��� �������� ������, �� �� ���� ������� ����������", e);
		}
	}

	/**
	 * ����������  ����������� ������  ����������� ������
	 * @param order �����
	 */
	public void refreshOrderNotificationStatus(Order order) throws BusinessException
	{
		order.setNotificationStatus(OrderStatus.NOT_SEND);
		order.setNotificationCount(0l);
		getSimpleService().addOrUpdate(order);
	}

	/**
	 *
	 * �������� ������� � ������.
	 * @param payment
	 * @param uuid
	 * @throws BusinessException
	 */
	public void linkPayment(String uuid, BusinessDocument payment) throws BusinessException
	{
		Order order = getOrder(uuid);
		if (order == null)
			throw new BusinessException("�� ���������� ������ � uuid = " + uuid);

		BusinessDocumentToOrder bdto = new BusinessDocumentToOrder();
		bdto.setOrderUuid(uuid);
		bdto.setId(payment.getId());
		simpleService.add(bdto);
	}
}
