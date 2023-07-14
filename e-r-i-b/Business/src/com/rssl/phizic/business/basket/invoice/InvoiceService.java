package com.rssl.phizic.business.basket.invoice;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.basket.invoiceSubscription.ErrorInfo;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.finances.financeCalendar.CalendarDayExtractByInvoiceSubscriptionDescription;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.basket.InvoiceStatus;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionErrorType;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author lepihina
 * @ created 13.05.14
 * $Author$
 * $Revision$
 * Сервис для работы с отложенными счетами (инвойсами)
 */
public class InvoiceService extends InvoiceServiceBase
{
	private static final String QUERY_PREFIX = Invoice.class.getName();

	private static SimpleService simpleService = new SimpleService();

	/**
	 * Получить инвойс по идентификатору
	 * @param id идентификатор
	 * @return инвойс
	 * @throws BusinessException
	 */
	public Invoice findById(Long id) throws BusinessException
	{
		if (id == null)
		{
			throw new IllegalArgumentException("Идентификатор инвойса не может быть null");
		}
		return simpleService.findById(Invoice.class, id);
	}

	/**
	 * Возвращает список из сущностей: отложенный счет + объект учета
	 * @param loginId - идентификатор клиента
	 * @param fromDate - начало периода поиска отложенных счетов
	 * @param toDate - конец периода поиска отложенных счетов
	 * @param invoiceFromDate - минимальная дата "живого" инвойса
	 * @param cardNumbers - список карт
	 * @param getCurrentInvoices флаг необоходимо ли получать текущие счета
	 * @return список
	 * @throws BusinessException
	 */
	public List<CalendarDayExtractByInvoiceSubscriptionDescription> findInvoicesToFinanceCalendar(final Long loginId, final Calendar fromDate, final Calendar toDate,
	                                                                                              final Calendar invoiceFromDate, final List<String> cardNumbers, final boolean getCurrentInvoices) throws BusinessException
	{
		if (CollectionUtils.isEmpty(cardNumbers))
			return Collections.emptyList();

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<CalendarDayExtractByInvoiceSubscriptionDescription>>()
			{
				public List<CalendarDayExtractByInvoiceSubscriptionDescription> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + (getCurrentInvoices ? ".findDelayedAndNewInvoicesToFinanceCalendar" : ".findDelayedInvoicesToFinanceCalendar"));
					query.setParameter("loginId", loginId);
					if (!getCurrentInvoices)
						query.setParameter("fromDate", fromDate);
					query.setParameter("toDate", toDate);
					query.setParameter("invoiceFromDate", invoiceFromDate);
					query.setParameterList("cardNumbers", cardNumbers);

					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Перевод всех "новых" инвойсов в рамках подписки в статус INACTIVE
	 * @param invoiceSubscriptionId идентификатор подписки
	 * @throws BusinessException
	 */
	public void inactivateAllByInvoiceSubId(final Long invoiceSubscriptionId) throws BusinessException
	{
		if (invoiceSubscriptionId == null)
		{
			throw new IllegalArgumentException("Идентификатор автопоиска счетов не может быть null");
		}
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					doInactive(invoiceSubscriptionId, session);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Обновить статус инвойса и очистить дату отложенности.
	 * @param status статус
	 * @param id  идентификатор
	 */
	public void changeStateById(final InvoiceStatus status, final Long id) throws BusinessException
	{
		if (status == null)
		{
			throw new IllegalArgumentException("Статус счета не может быть null");
		}
		if (id == null)
		{
			throw new IllegalArgumentException("Идентификатор счета не может быть null");
		}
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.basket.invoice.Invoice.setStateAndDelayDateById");
					query.setLong("id", id);
					query.setParameter("state", status.name());
					//обязательно чистим дату отложенности при оплате/удалении инвойса,
					//иначе механизм добавления новых инвойсов с учетом отложенности старых не будет работать.
					query.setCalendar("delayedPayDate", null);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Обновление данных в инвойсе при оплате счета. Простеновка статуса и внутреннего идентификатора платежа.
	 * @param invoiceId - идентфикатор инвойса
	 * @param paymentId - внутренний идентификатор платежа
	 */
	public void payInvoice(final Long invoiceId, final Long paymentId) throws BusinessException
	{
		if (invoiceId == null)
		{
			throw new IllegalArgumentException("Идентификатор счета не может быть null");
		}
		if (paymentId == null)
		{
			throw new IllegalArgumentException("Идентификатор платежа не может быть null");
		}
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.basket.invoice.Invoice.payInvoice");
					query.setLong("id", invoiceId);
					query.setLong("paymentId", paymentId);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param statuses статусы, отправленные вызывающим кодом
	 * @return нужно ли отбирать только отложенные инвойсы
	 */
	private boolean isQueryForDelayedOnly(final InvoiceStatus ...statuses)
	{
		return statuses.length == 1 && statuses[0] == InvoiceStatus.DELAYED;
	}

	/**
	 *
	 * @param statuses  статусы, отправленные вызывающим кодом
	 * @return Имя SQL-запроса по набору статусов
	 */
	private String getInvoicesListNameQuery(final  InvoiceStatus ...statuses)
	{
		if (isQueryForDelayedOnly(statuses))
		{
			return "com.rssl.phizic.business.basket.invoice.Invoice.list.delayed";
		}

		return "com.rssl.phizic.business.basket.invoice.Invoice.list";
	}

/**
	 * Получить все инвойсы по логину пользователя
	 * @param loginId - идентификатор логина
     * @param limit - ограничение на количество счетов
     * @param fromDate - дата начала отсчёта
	 * @return - список инвойсов
	 * @throws BusinessException
	 */
	public List<FakeInvoice> getInvoices(final Long loginId, final Long limit, final Calendar fromDate, final InvoiceStatus ...statuses) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<FakeInvoice>>()
			{
				public List<FakeInvoice> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(getInvoicesListNameQuery(statuses));
					query.setParameter("loginId", loginId);
					query.setParameter("limit", limit);
					query.setParameter("fromDate", fromDate);
					if (!isQueryForDelayedOnly(statuses))
					{
						query.setParameter("getDelayed", Boolean.toString(ArrayUtils.contains(statuses, InvoiceStatus.DELAYED)));
					}
					return (List<FakeInvoice>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить количество инвойсов пользователя
	 * @param loginId - идентификатор логина
	 * @param fromDate - дата отсчёта (инвойсы созданные до неё не считаем)
	 * @return - количество инвойсов
	 * @throws BusinessException
	 */
	public long getAmountOfInvoices(final Long loginId, final Calendar fromDate) throws BusinessException
	{
		try
		{
			List<Object> data = (List<Object>) HibernateExecutor.getInstance().execute(new HibernateAction<List>()
			{
				public List run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.basket.invoice.Invoice.count");
					query.setParameter("loginId", loginId);
					query.setParameter("fromDate", fromDate);
					return query.list();
				}
			});
			return (Long)data.get(0);

		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Обновление данных об ошибке в подписке
	 * @param operUID идентификатор заявки создания
	 * @param type тип ошибки
	 * @param errorDesc описание ошибки
	 * @throws BusinessException
	 */
	public void updateInvoiceSubErrorByOperUID(final String operUID, final InvoiceSubscriptionErrorType type, final ErrorInfo errorDesc) throws BusinessException
	{
		if(StringHelper.isEmpty(operUID))
			throw new IllegalArgumentException("operUID не может быть пустым.");

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription.updateInvoiceSubErrorByOperUID");
					query.setParameter("errorType", type == null ? null : type.name());
					query.setParameter("errorDesc", ErrorInfo.buildErrorDesc(errorDesc));
					query.setParameter("requestId", operUID);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Обновление данных об ошибке в подписке
	 * @param invoiceSubId идентификатор подписки
	 * @param type тип ошибки
	 * @param errorDesc описание ошибки
	 * @throws BusinessException
	 */
	public void updateInvoiceSubErrorById(final Long invoiceSubId, final InvoiceSubscriptionErrorType type, final ErrorInfo errorDesc) throws BusinessException
	{
		if(invoiceSubId == null)
			throw new IllegalArgumentException("invoiceSubId не может быть null.");

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription.updateInvoiceSubErrorById");
					query.setParameter("errorType", type == null ? null : type.name());
					query.setParameter("errorDesc", ErrorInfo.buildErrorDesc(errorDesc));
					query.setParameter("id", invoiceSubId);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить инвойс с типом подтверждения подписки по идентификатору
	 * @param invoiceId идентификатор инвойса
	 * @return [инвойс, тип стратегии подтверждения подписки]
	 * @throws BusinessException
	 */
	public Pair<Invoice, ConfirmStrategyType> getInvoiceWithConfirmStrategyType(final Long invoiceId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Pair<Invoice, ConfirmStrategyType>>()
			{
				public Pair<Invoice, ConfirmStrategyType> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(InvoiceSubscription.class.getName() + ".getInvoiceWithConfirmStrategyTypeById");
					query.setParameter("invoiceId", invoiceId);

					//noinspection unchecked
					Object[] res = (Object[]) query.uniqueResult();

					if (res == null || res.length == 0)
					{
						return new Pair<Invoice, ConfirmStrategyType>();
					}

					return new Pair<Invoice, ConfirmStrategyType>((Invoice) res[1], StringHelper.isEmpty((String) res[0]) ? null : ConfirmStrategyType.valueOf((String) res[0]));
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить инвойс с типом подтверждения подписки по идентификатору
	 * @param invoiceId идентификатор инвойса
	 * @return [инвойс, тип стратегии подтверждения подписки]
	 * @throws BusinessException
	 */
	public InvoiceUpdateInfo findInvoiceUpdateInfo(final Long invoiceId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<InvoiceUpdateInfo>()
			{
				public InvoiceUpdateInfo run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(InvoiceUpdateInfo.class.getName() + ".findById");
					query.setParameter("invoiceId", invoiceId);
					//noinspection unchecked
					return (InvoiceUpdateInfo) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
