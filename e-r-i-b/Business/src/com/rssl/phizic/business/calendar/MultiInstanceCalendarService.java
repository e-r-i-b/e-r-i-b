package com.rssl.phizic.business.calendar;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author mihaylov
 * @ created 27.01.14
 * @ $Author$
 * @ $Revision$
 */
public class MultiInstanceCalendarService
{
	private static final String FILTER_NAME = "calendar_filter_by_department";

	private static final MultiInstanceSimpleService multiInstanceSimpleService = new MultiInstanceSimpleService();
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();

	/**
	 * @param instanceName ������� ��
	 * @return ������ ���� ���������� �������
	 * @throws BusinessException
	 */
	public List<WorkCalendar> getAllCalendars(String instanceName) throws BusinessException
	{
		return multiInstanceSimpleService.getAll(WorkCalendar.class,instanceName);
	}

	/**
	 * ���������� ��� ��������� ������� ���������� ���������.
	 * @param employeeLoginId ������������� ����������
	 * @param instanceName ������� ��
	 * @return ������ ����������
	 * @throws BusinessException
	 */
	public List<WorkCalendar> getAllowedCalendars(final Long employeeLoginId, String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<List<WorkCalendar>>()
			{
				public List<WorkCalendar> run(Session session) throws Exception
				{
					Criteria criteria = session.createCriteria(WorkCalendar.class);
					Filter filter = session.enableFilter(FILTER_NAME);
					filter.setParameter("employeeLoginId",employeeLoginId);
					//noinspection unchecked
					return (List<WorkCalendar>) criteria.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ���������
	 * @param calendar ���������
	 * @param instanceName ������� ����
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void addOrUpdate(final WorkCalendar calendar, String instanceName) throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.saveOrUpdate(calendar);
					dictionaryRecordChangeInfoService.addChangesToLog(session, calendar, ChangeType.update);
					return null;
				}
			});
		}
		catch(ConstraintViolationException e)
		{
			throw new DublicateCalendarException(e);
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������� ���������� ���������
	 * @param calendar - ��������� ��� ��������
	 * @param instanceName - ������� ��, � ������� ���������� ��������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void remove(final WorkCalendar calendar, String instanceName) throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.delete(calendar);
					dictionaryRecordChangeInfoService.addChangesToLog(session, calendar, ChangeType.delete);
					session.flush();
					return null;
				}
			});
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("��������� " + calendar.getName() + " ������������, ��� ���������� �������.",e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� ��������� �� ��������������
	 * @param id - ������������� ���������
	 * @param instanceName - ������� ��, � ������� ���������� �����
	 * @return ���������
	 * @throws BusinessException
	 */
	public WorkCalendar findCalendarById(Long id, String instanceName) throws BusinessException
	{
		return multiInstanceSimpleService.findById(WorkCalendar.class, id, instanceName);
	}
}
