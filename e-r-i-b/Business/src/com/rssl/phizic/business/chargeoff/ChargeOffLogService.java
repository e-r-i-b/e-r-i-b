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
	 * �������� ����� ������������ �� ����������� ���������� �����
	 * @param login
	 * @return ������ ������������ �������� �� �������� ����������� �����
	 */
	public List<ChargeOffLog> getPersonsDebts(final CommonLogin login) throws BusinessException
	{
		return getChargeOffLogs(login, null, ChargeOffState.dept, null,null);
	}

	/**
	 * �������� ������� ����� ���� �������������
	 * @return ��� ������������ �����
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
	 * �������� ������� ����� ���� �������������
	 * @param ids - ������ ���������� ����� ������, ������� � ������� ����� ������
	 * @param maxRows ������������ ���������� ����� (��� �������� ��������� � Job)
	 * ���� maxRows == -1, �� ����������� ���������� ������� �� ����������
	 * @return ��� ������������ �����
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
	 * �������� ��� ��������� ������� ��� ������. ����� ������� �� ������, ������ ���������� ���� ������ (dept), ���� ���������� (payed)
	 * @return ��� ��������� �������
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
	 * �������� ��� ��������� ������� ��� ������. ����� ������� �� ������, ������ ���������� ���� ������ (dept), ���� ���������� (payed)
	 * @param maxRows ������������ ���������� ����� (��� �������� ��������� � Job)
	 * ���� maxRows == -1, �� ����������� ���������� ������� �� ����������
	 * @return ��� ��������� �������
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
	 * ������ ����� � ����������� � �������� ��������
	 * @param login - ����� �������
	 * @param operation_date - ���� ���������� ��������. ��� ������ ���� ��������� �������
	 * @param state - ������ �������
	 * @param periodFrom - ������ ������ (��������� ����)
	 * @return ������ ��������
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
						throw new BusinessException("login �� ����� ���� null");
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
	���������� ��������� � ������
	 */
	void write(ChargeOffLog chargeOff) throws BusinessException
	{
		simpleService.addOrUpdate(chargeOff);
	}

	/**
	 * �������������� ������, ���� ��� ����������� ������ (��� �����������), ���� ��� ������ ����� ����� (����������� �����)
	 * @param amount - ����� ��������
	 * @param login  - ����� �������
	 * @param type - ��� �������. ���� ChargeOffType.
	 * @param periodFrom - ������ ������� ������. ��� ����������� ����� �������� ������ �������, ����� �������������.
	 * @return �������������� ������ (�� �������� prepared)
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
	������� � ���������� ������. �������� ���������� ���������� prepareLog.
	 */
	void createAndWrite(Money amount, CommonLogin login, ChargeOffType type, Calendar periodFrom)  throws BusinessException
	{
		ChargeOffLog chargeOffLog = prepareLog(amount, login, type, periodFrom);
		write(chargeOffLog);
	}
}
