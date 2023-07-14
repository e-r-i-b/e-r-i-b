package com.rssl.phizic.business.chargeoff;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.utils.DateHelper;

import java.util.List;
import java.util.GregorianCalendar;
import java.util.Calendar;

import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.type.CalendarType;

/**
 * @author Egorova
 * @ created 15.01.2009
 * @ $Author$
 * @ $Revision$
 */
public class ChargeOffLogService
{
	private static SimpleService simpleService = new SimpleService();

	/**
	 * Получить долги пользователя по ежемесячной абоненской плате
	 * @param login
	 * @return список неоплаченных платежей по списанию ежемесячной платы
	 */
	public List<ChargeOffLog> getPersonsDebts(final CommonLogin login) throws BusinessException
	{
		return getChargeOffLogs(login, null, ChargeOffState.dept, null,null);
	}

	/**
	 * Получаем текущие долги всех пользователей
	 * @return все неоплаченные долги
	 * @throws BusinessException
	 */
	public List<ChargeOffLog> getAllDebts() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ChargeOffLog>>()
			{
				public List<ChargeOffLog> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.chargeoff.getDebts");
					//noinspection unchecked
					return (List<ChargeOffLog>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

	}

	/**
	 * Получаем текущие долги всех пользователей
	 * @param ids - список уникальных кодов долгов, которые в запросе нужно отсечь
	 * @param maxRows максимальное количество строк (для пакетной обработки в Job)
	 * Если maxRows == -1, то ограничение количества записей не происходит
	 * @return все неоплаченные долги
	 * @throws BusinessException
	 */
	public List<ChargeOffLog> getAllDebtsForJob(final List<Long> ids, final int maxRows) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ChargeOffLog>>()
			{
				public List<ChargeOffLog> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery((ids==null||ids.size()==0) ?
							"com.rssl.phizic.business.chargeoff.getDebts" :
							"com.rssl.phizic.business.chargeoff.getDebtsWithFilter");
					if(!(ids == null || ids.size() == 0))
			            query.setParameterList("ids", ids);
					if (maxRows != -1)
						query.setMaxResults(maxRows);
					//noinspection unchecked
					return (List<ChargeOffLog>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

	}

	/**
	 * Получаем все очередные платежи для оплаты. После первого же вызова, платеж становится либо долгом (dept), либо оплаченным (payed)
	 * @return все очередные платежи
	 * @throws BusinessException
	 */
	public List<ChargeOffLog> getAppropriativePayments() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ChargeOffLog>>()
			{
				public List<ChargeOffLog> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.chargeoff.getAppropriativePayments");
					Calendar calendar = new GregorianCalendar();
					query.setParameter("currentDate", calendar);
					//noinspection unchecked
					return (List<ChargeOffLog>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

	}

	/**
	 * Получаем все очередные платежи для оплаты. После первого же вызова, платеж становится либо долгом (dept), либо оплаченным (payed)
	 * @param maxRows максимальное количество строк (для пакетной обработки в Job)
	 * Если maxRows == -1, то ограничение количества записей не происходит
	 * @return все очередные платежи
	 * @throws BusinessException
	 */
	public List<ChargeOffLog> getAppropriativePaymentsForJob(final List<Long> ids, final int maxRows) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ChargeOffLog>>()
			{
				public List<ChargeOffLog> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery((ids==null||ids.size()==0) ?
							"com.rssl.phizic.business.chargeoff.getAppropriativePayments" :
							"com.rssl.phizic.business.chargeoff.getAppropriativePaymentsWithFilter");
					Calendar calendar = new GregorianCalendar();
					query.setParameter("currentDate", calendar);
					if(!(ids == null || ids.size() == 0))
			            query.setParameterList("ids", ids);
					if (maxRows != -1)
						query.setMaxResults(maxRows);
					//noinspection unchecked
					return (List<ChargeOffLog>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

	}

	/**
	 * Список оплат в соответсвии с заданным фильтром
	 * @param login - логин клиента
	 * @param operation_date - дата совершения операции. Для долгов дата последней попытки
	 * @param state - статус платежа
	 * @param periodFrom - период оплаты (начальная дата)
	 * @return Список платежей
	 * @throws BusinessException
	 */
	public List<ChargeOffLog> getChargeOffLogs(final CommonLogin login, final Calendar operation_date, final ChargeOffState state, final Calendar periodFrom, final Integer maxRows) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ChargeOffLog>>()
			{
				public List<ChargeOffLog> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.chargeoff.getChargeOffLogs");
					if (login==null)
						throw new BusinessException("login не может быть null");
					query.setParameter("login", login);
					query.setParameter("operation_date", operation_date, new CalendarType());
					query.setParameter("state", (state!=null)?state.toString():null);
					query.setParameter("periodFrom", periodFrom, new CalendarType());
					if (maxRows!=null && maxRows > 0)
							query.setMaxResults(maxRows);
					//noinspection unchecked
					return (List<ChargeOffLog>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

	}

	/*
	Записываем сообщение в журнал
	 */
	void write(ChargeOffLog chargeOff) throws BusinessException
	{
		simpleService.addOrUpdate(chargeOff);
	}

	/**
	 * Подготавливаем платеж, либо для немедленной оплаты (при подключении), либо для оплаты через месяц (ежемесячная плата)
	 * @param amount - сумма списания
	 * @param login  - логин клиента
	 * @param type - тип платежа. типа ChargeOffType.
	 * @param periodFrom - начало периода оплаты. При ежемесячной плате передаем начала периода, конец высчитывается.
	 * @return подготовленный платеж (со статусом prepared)
	 */
	ChargeOffLog prepareLog(Money amount, CommonLogin login, ChargeOffType type, Calendar periodFrom)
	{
		ChargeOffLog chargeOff = new ChargeOffLog();
		GregorianCalendar date = new GregorianCalendar();
		chargeOff.setDate(date);

		chargeOff.setLogin(login);
		chargeOff.setAmount(amount);
		chargeOff.setType(type);
		if(periodFrom!=null)
		{
			chargeOff.setPeriodFrom(periodFrom);
			if (ChargeOffType.connection.compareTo(type) != 0)
			{
				Calendar newPeriod = (Calendar) periodFrom.clone();
				Calendar periodUntil = DateHelper.addSolarMonth(newPeriod, newPeriod.get(Calendar.DATE));
				chargeOff.setPeriodUntil(periodUntil);
			}
		}

		chargeOff.setState(ChargeOffState.prepared);
		return chargeOff;
	}

	/*
	Создаем и записываем платеж. значение параметров аналогично prepareLog.
	 */
	void createAndWrite(Money amount, CommonLogin login, ChargeOffType type, Calendar periodFrom)  throws BusinessException
	{
		ChargeOffLog chargeOffLog = prepareLog(amount, login, type, periodFrom);
		write(chargeOffLog);
	}
}
