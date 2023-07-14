package com.rssl.phizic.business.bki;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.person.Person;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;

/**
 * @author Gulov
 * @ created 30.09.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��� ������ � ��������� �������� �������
 */
public class CreditProfileService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * ��������� �������
	 * @param profile ��������� �������
	 * @throws BusinessException
	 */
	public void save(PersonCreditProfile profile) throws BusinessException
	{
		simpleService.addOrUpdate(profile);
	}

	/**
	 * ����� ������� �� �������
	 * @param person ������
	 * @return �������
	 * @throws BusinessException
	 */
	public PersonCreditProfile findByPerson(final Person person) throws BusinessException
	{
		return findByPersonId(person.getId());
	}

	/**
	 * ����� ������� �� �������
	 * @param personId id �������
	 * @return �������
	 * @throws BusinessException
	 */
	public PersonCreditProfile findByPersonId(final long personId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<PersonCreditProfile>()
			{
				public PersonCreditProfile run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.bki.findByPersonId");
					query.setLong("personId", personId);
					return (PersonCreditProfile) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������� ��������� �� �������� � ��������������� ������ ����
	 * @param maxNewProfiles - ����������� ������ �� ���������� ����� ��������
	 * @return
	 */
	public int createCreditProfiles(final int maxNewProfiles) throws BusinessException
	{
		try
		{
			ExecutorQuery executorQuery = new ExecutorQuery(HibernateExecutor.getInstance(),
					"com.rssl.phizic.business.bki.PersonCreditProfile.createCreditProfiles");
			executorQuery.setParameter("service_key", "CreditReportPayment");
			executorQuery.setParameter("max_results", maxNewProfiles);
			return executorQuery.executeUpdate();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������� ��������, �� ������� �� �������� ���������� � ��������� �������.
	 * ���� ������ �������, �� �������:
	 * + ������ � ��� ��� �� �������
	 * + ������ � ��� ������� � ������� "������", �� ����� �� ��� �������, ���� �� ������� �� ���� ��������� �������
	 * @param checkRequestMaxTime - ����������� "�� ���������� ��������, �� ������� ������� �������� ������� �����������" (never null)
	 * @param personMaxCount - ������������ ����� �������� �� ������
	 * @return ������ ��������, ��� ������� �� ��������: ���� � ��� �� ��� ��� (never null can be empty)
	 */
	public List<ActivePerson> queryBKIUncheckedPersons(final Calendar checkRequestMaxTime, final int personMaxCount) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ActivePerson>>()
			{
				public List<ActivePerson> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.bki.PersonCreditProfile.queryBKIUncheckedPersons")
							.setCalendar("rqTime", checkRequestMaxTime)
							.setMaxResults(personMaxCount);
					//noinspection unchecked
					return (List<ActivePerson>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ����� ���������� ������� �������� ������� ��������� �������
	 * @param id ��������� �������
	 * @param date ����+����� �������� �������
	 */
	public void updateLastCheckRequestTime(final Long id, final Calendar date) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.bki.PersonCreditProfile.updateLastCheckRequestTime")
							.setParameter("id", id)
							.setParameter("rqTime", date);
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
}
