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
 * Интерфес, сервисов для работы с внешними поставщиками.
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
	 * Возвращает поставщика по имени поставщика.
	 * @param systemName название поставщика.
	 * @return поставщик.
	 */
	public InternetShopsServiceProvider getRecipientIdBySystemName(String systemName) throws BusinessException
	{
		return providerService.getRecipientActivityBySystemName(systemName);
	}

	/**
	 * Возвращает поставщика по идентификатору заказа.
	 * @param uuid идентификатор заказа.
	 * @return поставщик.
	 */
	public InternetShopsServiceProvider getRecipientByOrderUuid(String uuid) throws BusinessException
	{
		Order order = getOrder(uuid);
		if (order == null)
			return null;

		return getRecipientIdBySystemName(order.getSystemName());
	}

	/**
	 * Получить количество переданных из ФНС документов для текущего входа в систему
	 * @param person - текущий пользователь
	 * @return общее количество документов ФНС для текущего входа в систему
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
	 * Зарегистрировать информацию по заказу из ФНС
	 * @param fns - поля заказа
	 * @throws BusinessException - ошибки сохранения в БД, ошибки нарушения целостности базы
	 */
	public OrderInfo registerGeneralInfo(FNS fns)   throws BusinessException
	{
		InternetShopsServiceProvider provider = getRecipientIdBySystemName(fns.getOrder().getSystemName());
		if (provider == null || !FNS_PAYMENT_FORM.equals(provider.getFormName()))
			throw new BusinessException("Попытка привязать  заказ из ФНС к стороннему или неактивному поставщику");

		return registerGeneralInfo((OrderInfo) fns);
	}

	/**
	 * Зарегистрировать информацию по заказу из УЭК
	 * @param uecOrder - заказ
	 * @throws BusinessException - ошибки сохранения в БД, ошибки нарушения целостности базы
	 */
	public Order registerUECOrder(Order uecOrder) throws BusinessException
	{
		try
		{
			// сохраняем данные
			return getSimpleService().addOrUpdateWithConstraintViolationException(uecOrder);
		}
		catch(BusinessException e)
		{
			throw new BusinessException("Ошибка при регистрации информации по заказу УЭК",  e);
		}

	}

	/**
	 * Зарегистрировать информацию по заказу
	 * @param info - поля заказа
	 * @return
	 * @throws BusinessException - ошибки сохранения в БД, ошибки нарушения целостности базы
	 */
	private OrderInfo registerGeneralInfo(OrderInfo info) throws BusinessException
	{
		try
		{
			// сохраняем данные
			return getSimpleService().addOrUpdateWithConstraintViolationException(info);
		}
		catch (ConstraintViolationException e)
		{
			Order order = info.getOrder();
			throw new BusinessException("Ошибка при регистрации информации по заказу: заказ с такими параметрами " +
					"(extendedId=" + order.getExtendedId() + ", systemName=" + order.getSystemName() + ") " +
					"уже был зарегистрирован ранее",  e);
		}
		catch(BusinessException e)
		{
			throw new BusinessException("Ошибка при регистрации информации по заказу",  e);
		}
	}

	/**
	 * Получает данные по заказу
	 * @param uuid  - идентификатор запроса
	 * @return null - если ничего не найдено; иначе заполненный объект
	 * @throws BusinessException: передан null, найдено больше 1-ой строки
	 */
	public OrderInfo getOrderInfo(String uuid)  throws BusinessException
	{
		if (uuid == null)
			throw new BusinessException("Неизвестный заказ uuid="+uuid);

		DetachedCriteria dc = DetachedCriteria.forClass(OrderInfo.class);
		dc.createAlias("order", "orderAlias");
		dc.add( Expression.eq("orderAlias.uuid", uuid));

		OrderInfo orderInfo =  getSimpleService().findSingle(dc);

		// если был закаказ был передан частично, то orderInfo нет, пробуем искать сам заказ
		if (orderInfo == null)
		{
			Order order = getOrder(uuid);

			if (order == null)
				throw new BusinessException("Не удается найти элементы заказа по uuid=" + uuid);

			orderInfo = new WebShop();  // у фнс все поля приходят всегда, поэтому это магазин с частичными данными
			orderInfo.setOrder(order);
		}

		return orderInfo;
	}

	/**
	 * Получаем основные поля заказа по идентификатору запроса (без возможности установить тип заказа: фнс или магазин)
	 * @param uuid - номер платежа
	 * @return - найденный заказ
	 * @throws BusinessException
	 */
	public Order getOrder(String uuid) throws BusinessException
	{
		if (uuid == null)
			throw new BusinessException("Неизвестный заказ");

		// получаем заказ по uuid
		DetachedCriteria dc = DetachedCriteria.forClass(Order.class);
		dc.add( Expression.eq("uuid", uuid));
		return  getSimpleService().findSingle(dc);
	}

	/**
	 * Получаем заказ по типу,  имени  системы и номеру заказа во внешней системе.
	 * @param extOrderNumber - номер заказа во внешней системе
	 * @param systemName - имя системы
	 * @return - найденный заказ
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
	 * проверяет по uuid, принадлежит ли данный заказ переданному пользователю
	 * @param uuid - идентификатор операции заказа
	 * @param person - пользователь
	 * @return true - принадлежит, false - нет
	 */
	public boolean isOrderForPerson(String uuid, Person person) throws BusinessException
	{
		DetachedCriteria dc = DetachedCriteria.forClass(Order.class);
		dc.add(Expression.eq("person.id", person.getId()));
		dc.add(Expression.eq("uuid", uuid));

		return (getSimpleService().findSingle(dc) != null);
	}

	/**
	 * Получаем УЭК-заказы, к кот. был привязан платеж со статусом "Исполнен", "Ошибка", "Отказан" ,
	 * и оповещение со статусом NOT_SEND(не отсылалось), ERROR(отсылалось но приходит ошибка)
	 * Статус NOT_SEND для заказов УЭК выставляется в момент перехода бизнес документа в статус "Исполнен", "Ошибка" или "Отказан".
	 * @param maxRows  - максимальное кол-во строк, которые нужно достать, -1 = без ограничений
	 * @param time
	 * @param notifyMaxCount - максимальное количество попыток отправки (д.б. положительным)
	 * @return список заказов из веб-магазинов, к которым был привязан платеж, но не было выслано оповещение
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
			throw new BusinessException("Ошибка получения УЭК-заказов, к которым был привязан платеж, но не было выслано оповещение", e);
		}
	}

	/**
	 * Установить  изначальный статус  уведомления заказа
	 * @param order заказ
	 */
	public void refreshOrderNotificationStatus(Order order) throws BusinessException
	{
		order.setNotificationStatus(OrderStatus.NOT_SEND);
		order.setNotificationCount(0l);
		getSimpleService().addOrUpdate(order);
	}

	/**
	 *
	 * Привязка платежа к заказу.
	 * @param payment
	 * @param uuid
	 * @throws BusinessException
	 */
	public void linkPayment(String uuid, BusinessDocument payment) throws BusinessException
	{
		Order order = getOrder(uuid);
		if (order == null)
			throw new BusinessException("Не существует заказа с uuid = " + uuid);

		BusinessDocumentToOrder bdto = new BusinessDocumentToOrder();
		bdto.setOrderUuid(uuid);
		bdto.setId(payment.getId());
		simpleService.add(bdto);
	}
}
